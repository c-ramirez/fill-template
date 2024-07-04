# fill-template
Given a template with a certain format, inserts values using regular expressions
There are three types supported:
- Parameters: Must have unique id in the template. 
  Example: &P{parameter1}
- List: All the content inside the list block will be repeated as many times as rows defined in the model 
  Example: &L[list]{{ 
    test
  }}
- Fields: Must have unique id inside the list block.
  Example: &L[list]{{
    test &F{field1}
  }}

Execute 
```
mvn clean install

```




