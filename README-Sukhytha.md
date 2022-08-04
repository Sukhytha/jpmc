# Introduction
 
1) While there is still lot of room of improvement, I have attempted to refine and modularize the given code base. Created different layers with seperate responsibilities. 
This will help in code maintainability, scalabilty and readability.
2) System has been enhanced with the new requirements.
3) Theatre is the entry point of the system. It is designed to be easy to integrate with and/or expose the features as a service if required
4) The models have been segregated. So we can effectively use them to communicate internally between services and externally. 
5) Discount rules have been made configurable(using properties file) for ease of changes. 
Alternatively they can be fetched from database, or a config managemnent tool can also to change the files on the fly
making it easier to add new promotion or update existing ones. 
6) Drools is just a recommendation here for rules engine. 
7) Exception handling has been introduced but has to be enhanced.
We could create custom exceptions(checked and unchecked) if we wish to handle them differently or add extra information for the caller than what standard java has to offer. 
There is no need to create custom exceptions if there is no value in doing so. 
8) Log handling has been introduced but has to be enhanced across the system. This will be vital when it comes to debug and root cause analysis in a production set up. 
9) Test framework: Mockitto can be used to write Junits and regression tests. 
The end to end(large) real tests can cover a few sunny day rainy day scenarios.
Extensive small(junits) and medium tests(can be mocked or real involves interacting code units) can be written using mockitto.
Between the combination of the large, medium and small tests we could achieve the level of test coverage desired.

## Instructions
* **Consider this to be your project! Feel free to make any changes**
* There are several deliberate design, code quality and test issues in the current code, they should be identified and resolved
* Implement the "New Requirements" below
* Keep it mind that code quality is very important
* Focus on testing, and feel free to bring in any testing strategies/frameworks you'd like to implement
* You're welcome to spend as much time as you like, however, we're expecting that this should take no more than 2 hours

## `movie-theater`

### Current Features
* Customer can make a reservation for the movie
  * And, system can calculate the ticket fee for customer's reservation
* Theater have a following discount rules
  * 20% discount for the special movie
  * $3 discount for the movie showing 1st of the day
  * $2 discount for the movie showing 2nd of the day
* System can display movie schedule with simple text format

## New Requirements
* New discount rules; In addition to current rules
  * Any movies showing starting between 11AM ~ 4pm, you'll get 25% discount
  * Any movies showing on 7th, you'll get 1$ discount
  * The discount amount applied only one if met multiple rules; biggest amount one
* We want to print the movie schedule with simple text & json format