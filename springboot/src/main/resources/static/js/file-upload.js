const CHUNK_SIZE = 10 * 1024 * 1024;
const CONCURRENCY = 3;
const MAX_RETRIES = 3;


// ==========================================================
// Upload File
// ==========================================================

async function uploadFile() {

    const fileInput =
        document.getElementById("file");

    const file =
        fileInput.files[0];

    if (!file) {
        alert("Please select a file");
        return;
    }

    const progress =
        document.getElementById("progress");

    try {

        // --------------------------------------------------
        // 1. Initiate multipart upload
        // --------------------------------------------------

        progress.innerText =
            "Preparing upload...";

        const initiateResponse =
            await fetch(
                "/api/uploads/initiate",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body: JSON.stringify({
                        fileName: file.name,
                        contentType:
                            file.type ||
                            "application/octet-stream",
                        fileSize: file.size
                    })
                }
            );

        if (!initiateResponse.ok) {

            throw new Error(
                "Failed to initiate upload"
            );
        }

        const upload =
            await initiateResponse.json();

        console.log(
            "Upload initialized:",
            upload
        );


        // --------------------------------------------------
        // 2. Upload chunks
        // --------------------------------------------------

        const completedParts = [];

        let nextPartIndex = 0;


        // --------------------------------------------------
        // Upload a single part
        // --------------------------------------------------

        async function uploadPart(part) {

            const start =
                (part.partNumber - 1) *
                CHUNK_SIZE;

            const end =
                Math.min(
                    start + CHUNK_SIZE,
                    file.size
                );

            const chunk =
                file.slice(
                    start,
                    end
                );

            console.log(
                `Uploading part ${part.partNumber}`
            );

            const response =
                await fetch(
                    part.url,
                    {
                        method: "PUT",
                        body: chunk
                    }
                );

            if (!response.ok) {

                throw new Error(
                    `Failed to upload part ` +
                    `${part.partNumber}`
                );
            }

            const etag =
                response.headers.get(
                    "ETag"
                );

            if (!etag) {

                throw new Error(
                    `ETag missing for part ` +
                    `${part.partNumber}`
                );
            }

            return {
                partNumber:
                part.partNumber,

                etag:
                etag
            };
        }


        // --------------------------------------------------
        // Retry failed part
        // --------------------------------------------------

        async function uploadPartWithRetry(part) {

            let attempt = 0;

            while (
                attempt < MAX_RETRIES
                ) {

                try {

                    return await uploadPart(
                        part
                    );

                } catch (error) {

                    attempt++;

                    console.error(
                        `Part ${part.partNumber} failed. ` +
                        `Attempt ${attempt}/${MAX_RETRIES}`,
                        error
                    );

                    if (
                        attempt >=
                        MAX_RETRIES
                    ) {

                        throw new Error(
                            `Part ${part.partNumber} ` +
                            `failed after ` +
                            `${MAX_RETRIES} attempts`
                        );
                    }

// Exponential backoff
// 1 sec -> 2 sec -> 4 sec

                    const delay =
                        Math.pow(
                            2,
                            attempt - 1
                        ) * 1000;

                    await new Promise(
                        resolve =>
                            setTimeout(
                                resolve,
                                delay
                            )
                    );
                }
            }
        }


// --------------------------------------------------
// Worker
// --------------------------------------------------

        async function worker() {

            while (true) {

                const index =
                    nextPartIndex++;

                if (
                    index >=
                    upload.parts.length
                ) {
                    return;
                }

                const part =
                    upload.parts[index];

                console.log(
                    `Starting part ` +
                    `${part.partNumber}`
                );

                const completedPart =
                    await uploadPartWithRetry(
                        part
                    );

                completedParts.push(
                    completedPart
                );

                const percentage =
                    Math.round(
                        (
                            completedParts.length /
                            upload.totalParts
                        ) * 100
                    );

                progress.innerText =
                    `Uploading... ${percentage}%`;

                console.log(
                    `Completed part ` +
                    `${part.partNumber}`
                );
            }
        }


// --------------------------------------------------
// Start parallel workers
// --------------------------------------------------

        const workers = [];

        for (
            let i = 0;
            i < CONCURRENCY;
            i++
        ) {

            workers.push(
                worker()
            );
        }

        await Promise.all(
            workers
        );


// --------------------------------------------------
// 3. Complete multipart upload
// --------------------------------------------------

        progress.innerText =
            "Completing upload...";

        const completeResponse =
            await fetch(
                "/api/uploads/complete",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body: JSON.stringify({
                        uploadId:
                        upload.uploadId,

                        key:
                        upload.key,

                        parts:
                        completedParts
                    })
                }
            );

        if (!completeResponse.ok) {

            throw new Error(
                "Failed to complete upload"
            );
        }

        progress.innerText =
            "Upload completed successfully!";


// Refresh file list
        await loadFiles();

    } catch (error) {

        console.error(error);

        progress.innerText =
            `Upload failed: ${error.message}`;
    }
}


// ==========================================================
// Load Files
// ==========================================================

async function loadFiles() {

    try {

        const response =
            await fetch(
                "/api/files"
            );

        if (!response.ok) {

            throw new Error(
                "Failed to load files"
            );
        }

        const files =
            await response.json();

        const fileList =
            document.getElementById(
                "fileList"
            );

        fileList.innerHTML = "";


        files.forEach(file => {

            const row =
                document.createElement(
                    "tr"
                );


            // File name
            const nameCell =
                document.createElement(
                    "td"
                );

            nameCell.innerText =
                file.fileName;


            // File size
            const sizeCell =
                document.createElement(
                    "td"
                );

            sizeCell.innerText =
                formatFileSize(
                    file.size
                );


            // Actions
            const actionCell =
                document.createElement(
                    "td"
                );


            // Download button
            const downloadButton =
                document.createElement(
                    "button"
                );

            downloadButton.innerText =
                "Download";

            downloadButton.onclick =
                () => downloadFile(
                    file.key
                );


            // Delete button
            const deleteButton =
                document.createElement(
                    "button"
                );

            deleteButton.innerText =
                "Delete";

            deleteButton.onclick =
                () => deleteFile(
                    file.key
                );


            actionCell.appendChild(
                downloadButton
            );

            actionCell.appendChild(
                document.createTextNode(
                    " "
                )
            );

            actionCell.appendChild(
                deleteButton
            );


            row.appendChild(
                nameCell
            );

            row.appendChild(
                sizeCell
            );

            row.appendChild(
                actionCell
            );


            fileList.appendChild(
                row
            );
        });

    } catch (error) {

        console.error(error);

    }
}


// ==========================================================
// Format File Size
// ==========================================================

function formatFileSize(bytes) {

    if (bytes === 0) {
        return "0 Bytes";
    }

    const units = [
        "Bytes",
        "KB",
        "MB",
        "GB"
    ];

    const index =
        Math.floor(
            Math.log(bytes) /
            Math.log(1024)
        );

    return (
        parseFloat(
            (
                bytes /
                Math.pow(
                    1024,
                    index
                )
            ).toFixed(2)
        ) +
        " " +
        units[index]
    );
}


// ==========================================================
// Download File
// ==========================================================

async function downloadFile(key) {

    try {

        const response =
            await fetch(
                `/api/files/download?key=` +
                `${encodeURIComponent(key)}`
            );

        if (!response.ok) {

            throw new Error(
                "Failed to download file"
            );
        }

        const url =
            await response.text();

        window.location.href =
            url;

    } catch (error) {

        console.error(error);

        alert(
            "Failed to download file"
        );
    }
}


// ==========================================================
// Delete File
// ==========================================================

async function deleteFile(key) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this file?"
        );

    if (!confirmed) {
        return;
    }

    try {

        const response =
            await fetch(
                `/api/files?key=` +
                `${encodeURIComponent(key)}`,
                {
                    method: "DELETE"
                }
            );

        if (!response.ok) {

            throw new Error(
                "Failed to delete file"
            );
        }

        // Refresh the file list
        await loadFiles();

    } catch (error) {

        console.error(error);

        alert(
            "Failed to delete file"
        );
    }
}


// ==========================================================
// Load files when page opens
// ==========================================================

loadFiles();