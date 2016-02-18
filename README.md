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



    

