# Chatbot
A simple chatbot that is able to understand basic who, what, where, when and how questions.
*Further improvements should be done*

Uses NLP tool for tokenizing and POS tag.
Implementation method was partially based on *Information Dissemination and Storage For Tele-Text Based Conversational Systems Learning, Ram Raj (2009)*
In simple terms, the idea is that the sentence is broken down into pieces in which can allow those questions to be solved quickly and efficiently.
The implementation differs abit as it uses TF-IDF for score ranking and also detects for negative sentences.

Sample information that can be fed
1. Siti mop the floor while Harry wipe the window
2. Harry ran away from school because he found it to be boring

Sample question that can be asked
1. a) Who mop the floor?
   b) What did Harry do?
   c) Did Harry wipe the window?

2. a) Who ran away from school?
   b) Why did Harry ran away?
   c) Where did Harry ran away from?
