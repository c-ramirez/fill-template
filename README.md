# fill-template
Given a template with a certain format, inserts values in a template using regular expressions
The types of permitted values are: 
 - Parameters: single value, only appear once in the template. &P{}
 - Fields: list of values &L[]{{  &F{}  }} 

A sample template is:

```
This is a sample text, here is the first parameter &P{parameter1}, here is the second &P{parameter2} here 
is the first parameter again &P{parameter1}.

Then we have a list 
&L[list]{{
	- This text should be repeated.
	- You can pass inner fields inside this list : (&F{field1})  : (&F{field2}) 
	- Another line
}}

```

This project was only made to explore the functioning of regular expresions.



