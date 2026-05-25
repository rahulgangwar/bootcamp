

nums = [1,2,5,4]
print(nums)
print(nums[1])

nums[1] = 3 # update index 1 to 3
print(nums)

print(nums[1:3]) # prints elements from index 1 to 2

# Insert
nums.append(10) # adds 10 at the end
print(nums)

nums.insert(2, 1000) # inserts 1000 at index 2
print(nums)

nums.remove(3)  # removes first occurrence of 3
print(nums)

nums.pop(1) # removes element at index 1
print(nums)

nums.sort()
print(nums)

nums.sort(reverse=True)
print(nums)

