# Testing

src/test/java/test/all class files present for testing
I have use JAVA , Selenium , testng , Maven for testing amazon website

Search for a non-existing product          "No results found" message should be
(e.g., "ld345tsxslfer")                     displayed 
 For this test case i use AmazonSearchTest.java class

 Search for an existing product              Product results should display "Laptop"    (e.g., "Laptop")                     
                                             on the page
 for this test case I use AmazonSearchExistingProductTest.java class

 Add a product to the cart                   Product (Select 4th result from list) should be added to the cart with                                                                     correct quantity and price details 
for this test case I use AmazonAddToCartTest.java class


Add a product to the cart, then               The cart should reflect the    
updated the quantity to 2                     update quantity and price 
for this test case I use AmazonAddProductAndUpdateQuantityTest.java class

 
Remove a product from the cart               The cart should be empty 
for this test case I use AmazonRemoveProductFromCartTest.java class 

#  YOU CAN RUN CODE BY RUN ON JAVA APPLICATION
