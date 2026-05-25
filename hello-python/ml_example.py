import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression

# Step 1: Create a dataset
data = {
    'hours_studied': [1, 2, 3, 4, 5, 6, 7, 8],
    'marks': [35, 40, 50, 55, 60, 65, 70, 75]
}

# Step 2: Load into pandas DataFrame
df = pd.DataFrame(data)

print("Dataset:")
print(df)

# Step 3: Prepare X (input) and y (output)
X = np.array(df['hours_studied']).reshape(-1, 1)  # reshape required for sklearn
y = np.array(df['marks'])

# Step 4: Train model
model = LinearRegression()
model.fit(X, y)

print("\nModel trained!")

# Step 5: Predict marks for a new student
hours = 9
predicted_marks = model.predict([[hours]])

print(f"\nIf a student studies {hours} hours, predicted marks = {predicted_marks[0]:.2f}")
