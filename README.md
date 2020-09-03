# ScravaJipt

ScravaJipt is a console based script language written in Java.

## Writing a script

### Instructions

A script in ScravaJipt consists of these following instructions:

Instruction syntax | Description
------ | -----
`set [variable] [value]` | Sets a  variable to a given value. This can be a string, number or boolean. ScravaJipt will recognize the data type automatically.
`print [value]` | Prints the value to console.
`println [value]` | Prints the value to the console and creates a new line.
`input [variable]` | Asks for an input and writes it to the given variable.
`if [condition]` | Runs the following code block, if the condition is true.
`else` | Runs if the `if` container above it had a false condition.
`while [condition]` | Runs the following code block over and over, until the condition is not true anymore.
`end` | Ends a code block.

### Variables

In a value, you can use `&[variable]` as a placeholder for variables. When using `set` or `input` the first parameter doesn't need to have an ampersand in front of it. For example:

```
set a 123
set b &a
set c "&a"
set d a
set e "a"
```

Now `a` and `b` are numbers with a value of 123, `c` is a string saying "123" and `d` and `e` are strings saying "a".

### Operators
#### Boolean Operators
There are the following operators for booleans:

- `&&` (And)
- `||` (Or)
- `==` (Equal)
- `!=` (Not equal)
- `>` (Greater than)
- `<` (Smaller than)
- `>=` (Greather than or equal)
- `<=` (Smaller than or equal)

#### Mathematical Operators
There are the following operators for maths:
- `+` (Addition)
- `-` (Subtraction)
- `*` (Multiplication)
- `/` (Division)
- `%` (Modulo)

#### Brackets
You can use brackets to override the default operator priority. For example:

`print 2*2+2` outputs 6.0

`print 2*(2+2)` outputs 8.0

This also works with boolean operators.

## Compiling a script

This is an example script file that checks for all prime numbers in a given range and prints them:

```
# This is a comment
// This too

set start 1
set end   1000

set numPrimes 0
set i &start
while &i <= &end

    set j 2
    set primeFound true

    while &j < &i

        if &i % &j == 0
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
```

We are going to save this example to `prime_numbers.sj`. Now copy `scravajipt.jar` to the same folder and run the following command:

``
java -jar scravajipt.jar compile prime_numbers.sj
``

This will compile the script and save it to `prime_numbers.prog`. Now you can run it with the following command:

`java -jar scravajipt.jar run prime_numbers.prog`

The output should look like this:

```
1.0 is a prime number
2.0 is a prime number
3.0 is a prime number
5.0 is a prime number
...
997.0 is a prime number
Found 169.0 Prime Numbers
```