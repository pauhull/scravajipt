################
set start 1
set end   1000
################


set numPrimes 0
set i &start
while &i <= &end

    set j 2
    set primeFound true

    while &j<&i

        if &i%&j==0
            set primeFound false
        end

        set j &j+1
    end

    if &primeFound
        print "&i is a prime number\n"
        set numPrimes &numPrimes+1
    end

    set i &i+1
end

print "Found &numPrimes Prime Numbers\n"