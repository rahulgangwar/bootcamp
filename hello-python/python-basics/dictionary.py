
user_data = {
    "name": "Alice",
    "age": 30
}
print(user_data)
print(user_data["name"])

user_data["address"] = "street 123"
print(user_data)

# user_data.pop("age")
# print(user_data)

# user_data.clear()
# print(user_data)

# iterate over keys
for key in user_data:
    print(key + ": " + str(user_data[key]))

# iterate over key-value pairs
for key, value in user_data.items():
    print(f"{key} -> {value}")

user_data_bkp = user_data.copy()
print("Backup:", user_data_bkp)

