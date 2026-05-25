nums = [1, 2, 3, 4, 5]

for num in nums:
    print(num)

print(len(nums))
print(range(len(nums)))

for i in range(len(nums)):
    nums[i] = nums[i] + 1

print(nums)

i = 0
while i < len(nums):
    i += 1
    if i >= len(nums) or nums[i] % 2 == 0:
        continue
    print(nums[i])
