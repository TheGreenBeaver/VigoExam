## Introduction
Usually, when there's a need to refactor some code, there's also some way to communicate with its actual author and to 
exactly define the way it should work. However, the situation was quite a bit different with this task, so I've 
been working under an assumption that I'm allowed to work creatively and to tweak every single part of the given code.
## The *DataConnection* interface
The presence of an interface usually means that there's a bunch of classes that should implement the same
functionality. In this exact project, though, it looks like the interface has been written to especially maintain the
functionality of the one and only subclass.<br/>
According to the name of the interface and its methods, the classes implementing it are supposed work with various 
files. Thus, I've decided to make these classes pass the paths of files they work with as arguments, instead of some 
strange variables that seem to only be meaningful for the *MyApp* class. For the same reason I've also changed the 
return type of these methods, since the only thing that really matters outside each exact class is the fact of 
successful data loading and saving. I also thought it would be nicer if all the exceptions are handled inside the 
methods without throwing them, so I removed the **throws** from their signatures.
## The *MyApp* class
* ### The pattern
    The original class had a static subclass that kinda worked like some strange Factory. The overall structure of the 
    class, though, doesn't really support the idea of a Factory Pattern since there's no variety of similar classes. 
    Not sure if such a plain construct even needs any pattern at all, but making it a Singleton seemed to me a way to
    preserve the greatest amount of initial code.
* ### The working methods
    First of all, I've removed a load of straight-up trash from the working methods, including strange manipulations
    with **byte** arrays or **Strings** and the permanently-zeroed-out variable **COUNT1**. I've added some exception 
    handling and cleaned up the logic. I've considered to work more on making the code readable than on making 
    it extremely short, so I've split all the operations between some variables with meaningful names.<br/>
    The way methods work has also been changed slightly. I got rid of some static class fields (and should have 
    probably got rid of them completely, since using static class fields in its methods might cause some painful 
    problems in a multi-thread ecosystem).<br/>
    Now all these methods do is exactly loading the data into the *MyApp* class and storing it into some file, without 
    any additional data manipulation outside them. The overall result remains the same: data gets processed and passed 
    to an outside storage. But the way the program does this now seems much less messy to me than it was initially.
* ### The *Main* method
    The changes made to working methods affected the way the *Main()* method works; there's no manipulation with numbers 
    needed in it anymore, only checking if loading and saving data was successful. I've also added the ability for the
    actual app user to choose the non-default files to read and write data by passing them as command line arguments.
## Something like an epilogue
The main problem while working on this task was the inability to get a piece of advice on the way the program is 
actually expected to work and the range of my own rights on modifying it. For example, I've had quite a hard time 
deciding what to do to the interface. Usually the interfaces are not to be touched since there might be tons of classes 
that hardly rely on this common pattern; here, though, the way this pattern was written seemed marginally absurd. The 
way the **COUNT** variable collaborated with the *Main()* and *loadData()* methods, either being or not being zeroed out 
according to the way you called the methods, also provides a considerable headache.<br/>
All this said, I'm sure the task would have been both much more interesting and understandable if there was a bit more 
context given to make decisions. Maybe even making the code a little bit more complicated functionally would be helpful.