local current = redis.call('GET', KEYS[1])

if current == false then
    redis.call('SET', KEYS[1], 1, 'EX', ARGV[1])
    return 1
end

if tonumber(current) < tonumber(ARGV[2]) then
    return redis.call('INCR', KEYS[1])
end

return -1