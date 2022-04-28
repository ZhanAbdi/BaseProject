# Automated tests API+Selenium WebDriver for the project

CSS selectors - unique combination(attribute=value)
    [attribute='value']
    #value = id="value"
    .value = class="value"
    SS maker/LightShot - programs for Screenshots


Absolute XPath:
/html/body/div[2]/div[1]/div[3]/div[6]/a/span
div[2] - parent div, div[1] - child div etc.

Relative XPath:
1. Unique combination:
//tag
//a[@data-purpose='header-login'] - //tag[@attribute='value']
//*[@attribute='value']

2. Index:
If there are several unique combinations, the index can be used to define a web element:
(//*[@attribute='value'])[3] - [index from 1 to 3]

3. Inner text (e.g.,<>Log in<>):
//*[text()='Log in']

4. Contains() - partial content of the inner text/partial attribute:
//*[contains(text(),'Log')]
//*[contains(@class,'reversed')]


Advanced XPath:
(//*[@attribute='value'])[3]/childtag/grandchildtag - relative & absolute XPath
(//*[@attribute='value'])[3]/a/span

To find a parent class upper:
//*[@attribute='value']/parent::*/parent::*/parent::*

To find a preceding sibling:
(//*[@attribute='value'])[3]/preceding-sibling::tag[2] - div[2] from the nearest/the end; * - all preceding tags

To find a following sibling:
(//*[@attribute='value'])[3]/following-sibling::*[2] - the second tag from the beginning


