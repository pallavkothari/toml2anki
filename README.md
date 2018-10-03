# toml2anki

### build
```
make
```

### run
You can prepare you anki decks in .toml format, and then use this tool to convert it to anki. 

Example:
```
build/install/toml2anki/bin/toml2anki samples/3.toml
```


This is convenient because you can use raw strings (`"""`) for either the front or the back of the cards: 

```toml
[[card]]
back = """
this is a raw multiline string
it may contain "quotes" or ;
"""
front = "test"
```

which gives the folling anki file (`samples/3.txt`): 
```
test;"this is a raw multiline string
it may contain ""quotes"" or ;
"
```
