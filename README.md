# Background

This is an experimental project that I am working on.

Logging in Java is a mess. Not only do we have several competing Logging 
Frameworks, but also we have Logging Facades sugercoating it all.

Second are the logs itself. How many application developers actually do read the
logs they are writing? What do you expect a sysadmin to do if he can't figure
out what is going on in your code? The logs are the only way your program can
communicate with the outside world. I happened  to come across my own logs in 
production. Pathetic!

# Structured logging with Key Value Pairs (KVPs)
While playing around with Splunk and Logentries I read about structured logging
with Key Value Pairs (KVPs). Unfortunately there is no direct library support.
I am just trying to find a simple way to create structured logs. Currently
I use `MessageFormat` all over the place but it is not pretty.

## Java Logging Frameworks and Logging Facades
* java.util.logging (JUL)
* Log4j
* Log4j 2
* tinylog
* Logback
* SLF4J
* JBoss LogManager
* Apache Commons Logging

# KVP Parsing Specification

This specification is copied from [Logentries](https://docs.logentries.com/docs/json#section-kvp-parsing-specification)

Specification

A key value pair consists of an identifier called a KEY, followed by a SEPARATOR, 
a VALUE and has a prefix and suffix termed the KVDELIMITER

KVDELIMITER KEY {whitespace} SEPARATOR {whitespace} VALUE{UNIT} KVDELIMITER

Term Definitions

KVDELIMITER

*can be space (" "), comma (","), tab "\t" or "\r" or "\n" or "\r\n"

KEY

the allowed list of characters is any Alphanumeric plus “_” “.” “$” “@”
must have at least one non-numeric character from the allowed list of characters
*a ” or ‘ either side of a key will result in anything between the quotations marks being interpreted as the KEY, e.g. “key[1]” will give a KEY of key[1]
{whitespace}
can be 0 or 1 spaces (Ascii 0x20 or ” “) SEPARATOR*

is “=” or “:”
can be preceded by and/or followed by a space
in order to appear in a key or value, the separator must be between a pair of quotes or escaped

VALUE

consists of any alphanumeric plus “_” “.” “$” “@”

a ” or ‘ either side of a value will result in anything between the quotations marks being interpreted as the VALUE, e.g. key1=”value description” will give a KEY of key1 and a VALUE of “value description”
Placing quotes around the VALUE is required for parsing URLs or other items that contain slashes (/)
*a SEPARATOR in a value will be treated such that this value (and the possible key preceding it) will not be treated as a key value pair, e.g. so timestamps not broken up

UNIT

This is the alphabetical text or “%” which follows the last number in a Numeric only VALUE up to the next space or first non alphabetical character or up to 2 characters (whichever occurs first) – those 2 characters will not be considered as part of value for querying, i.e. 100ms is treated as 100.

CHARACTER ESCAPING

this is used to allow a KEY or VALUE to contain a character that would not otherwise be allowed, e.g. a space or quote or comma or bracket is not allowed in a KEY or VALUE unless escaped.
this is supported in both a KEY and a VALUE by adding a backslash (“\”) in front of the character to be escaped or by including the entire KEY or VALUE in quotes (pairs of ” or pairs of ‘). As per the definition of KEY and VALUE, the \ is not an allowed character and only appears to support escaping, e.g a space can be included by either using quotes as in name=”David Tracey” or escaping as in name=David\ Tracey. Regarding quotes being in pairs of ” or pairs of ‘, this means that a ‘ does not finish an escaped sequence started with ” and it is possible for one type of quote to be appear within quotes of the other type, e.g. somekey = “David’s One” will include the ‘ in the value.
note that the \ is only treated as escaping a character in the process of determining a name or a value
Notes

“Key=value” “Key :value” “Key = value” “Key: value” are valid key value pairs
“-“, “+” are not allowed in the KEY or VALUE (unless escaped) fields to avoid confusion with use in the query language
this definition means a Key Value Pair must have both a key and a value the inclusion of “.” and “_” means that “cpu.time” and “disk_size” are both valid as a KEY or VALUE
UTF-8 Encoding is the supported encoding



    

