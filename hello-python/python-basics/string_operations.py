"""
name = "rahul"
print(f"hii {name.upper()}")
"""


sentence = "hi {} {}. Your age is: {}"
first_name = "rahul"
last_name = "babu"
age = input("enter your age: ")
print(sentence.format(first_name, last_name, age))