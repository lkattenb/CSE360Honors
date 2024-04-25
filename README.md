# CSE360Honors
Luke Kattenbraker
Professor Robert Lynn Carter
4/24/2024
https://github.com/lkattenb/CSE360Honors
CSE 360 Honors Project Final Report
	The Problem / Where the Idea Began
The idea of this problem came to me far before I enrolled in CSE 360 and began this project. As I approach the age where I will be owning my own house, I start to think about issues that I may face as I get older. I asked the question “How do homeowners manage their electricity usage in the household?” I thought about electricity bills and how you could reduce them. I thought about how much electricity I use on a given day and how it affects the environment as well. I then realized that I had thought of saving money before saving the environment, which is where the idea for this application came into play. I had an idea for an application that would advertise saving the user money while also leading to positive environmental impacts -a win win situation.
 I got an idea for an app that is something of a household electricity monitor with a focus in saving the user money. After some research, I found out that a company called Emporia is employing a household electricity monitoring system. Though, this company does not market with a focus on saving the user money. I believe that this is the reason that the company has not had widespread success. My application aims to bring value to one of ASU’s EOP initiatives by using technology to solve a sustainability issue. By using this technology, and a little bit of clever marketing, this application can help address the excessive electricity usage problem that we face today.

Bringing the Idea to Life
	Having an idea is one thing, but to bring it to life is another. I found that this process of the project was the most intimidating. Though, by using an incremental development process, I was able to make my application a success. I came up with an initial direction of where I wanted to go and created versions of the app with more and more functionality as I went on. The direction I decided on was creating a “mock” application. I would give the user a preset house with preset appliances and kWh usage. The user would then have two pages: electricity usage and the ways to save electricity.
	In the first iteration of the project I focused solely on research collected data and simple print outs for the modified data -no user interface implemented. I set up all of the backend global variables, constant values, and completed the necessary calculations. No user input was needed at this stage as this was all for backend functionality. This is where I created the “model” household that would be used in this application. After everything seemed to be in order and a ballpark figure was made for the total cost of electricity over one year, I was ready to move onto phase two.
	The second iteration of the project was the most challenging of them all. I began to use the JavaFX library in order to bring the application to a better user experience. I first added two pages to my application. One page where you could see your costs and one page where you could push a button to implement energy saving methods and see the updated cost on another page. Though, I felt as if something was missing. This is what led to the “graphs” page in the current application. I knew that there was public data on the surge time estimations from EIA (Energy Information Administration) and thought of ways that I could get it onto my application to dynamically tell the user whenever there would be a surge in electricity costs on any given day. This would allow the user to save even more money! I attempted to use a JSON API in my Java application to automatically pull this data through an SSH request. Though, there was too much complexity and not enough knowledge of JSON to complete this task alone. I instead took the data from the EIA on a single day and put it into a JavaFX graph. I added a space in the code where JSON implementation could be added in the future. After making sure that all of the pages looked right, I finished this stage and got ready for the third and final stage.
	The third iteration was to connect this user interface made in the second stage to the working backend created in iteration one. This proved to be very difficult as connecting the dots was not always simple. Especially when it came to creating similar pieces of code over and over, it was easy to get mixed up in the mess of JavaFX formatting in code. Though, I was able to finish up the application, add a few decorations and finish the final product. The last section was not incredibly difficult, which made me realize that I had a good “idea to architecture” plan. 

The Development Process and How it Worked Well
Using the incremental development process I was able to tackle an idea for an application in little steps that made implementation easier as more work was done. I was also able to easily monitor my progress and be able to easily report what I had done.
 I began by creating a framework of what was expected in this project. I was able to find issues in implementation very easy with a barebones project. After this had been completed, I was able to create a canvas where this framework could go. After creating both pieces of the puzzle, all I had to do in the end was put the two pieces together to create the final product. I think that by using an incremental development process you are much more likely to finish a large project like this as a solo developer.
Looking Forward
	Looking back on this project, I think that I chose the best development process being a solo developer. I was able to easily develop my idea, I was able to easily report what I had completed, and it took the stress off of crunching for this project as I knew exactly what had to be done by what time. Though, I think that this application has lots of room for improvement.
	I really tried to implement my automatic updating graph for electricity surges, but I was unable to make this happen as my knowledge of how JSON works is non-existent. Though, I do think that the idea could pull in and hook users who decide to use the application. I also hope to expand this application so that it can actually be used to monitor real time electricity usage in the home. Being able to look at the real time electricity usage of a device, I believe that this app could give real time suggestions to help the user save money -as well as saving the environment.
	
Final Thoughts
I hope that this idea can progress further with time. There has been such a struggle to get large groups of people on board with sustainable practices. I think that the first steps of  making these better environmental decisions as a society is by pushing out ideas like this: ideas where the advertised incentive is not “saving the planet”, but an incentive that is beneficial to the user. I think that this works especially well when it comes to money. I hope that with ideas like mine, which mask the true intention of making better environmental decisions, we can better tackle sustainability issues as a society. I especially think that with ASU’s EOP initiative of using technology to address sustainability issues, this idea can be achieved.



Sources
For Electricity Usage in the Southwest: https://www.eia.gov/electricity/gridmonitor/dashboard/electric_overview/regional/REG-SW
NYT Energy Predictions: https://www.nytimes.com/interactive/2024/03/13/climate/electric-power-climate-change.html
Average Arizona kWh prices: https://www.electricchoice.com/electricity-prices-by-state/
Average AC Electricity kWh: https://www.inspirecleanenergy.com/blog/sustainable-living/how-much-electricity-does-air-conditioning-use
Saving on AC Methods: https://www.nrdc.org/stories/how-save-your-energy-bills
Different Light Bulb kWh: https://www.huttonpowerandlight.com/blog/led-vs-incandescent-lighting-a-cost-comparison/#:~:text=To%20illustrate%20this%2C%20let's%20compare,of%20electricity%20every%201%2C000%20hours.
More Light Bulb Information: https://www.washingtonpost.com/lifestyle/home/why-you-should-switch-to-led-lightbulbs-right-now-before-the-law-requires-it/2018/01/16/c2d915f4-f0a2-11e7-b3bf-ab90a706e175_story.html
Cold Washer Setting: https://www.statesvillenc.net/2024/04/01/7585/did-you-know-that-you-can-save-on-your-energy-bill-by-washing-your-clothes-in-cold-water
Saving on Dryer Electricity: https://www.cenhud.com/en/my-energy/save-energy-money/blog/10-tips-to-save-energy-in-the-laundry-room/
Oven Electric vs. Gas: https://www.constellation.com/guides/appliances/energy-efficient-ovens-stoves.html#:~:text=It%20takes%20three%20times%20as,to%20run%20a%20gas%20range.
Dishwasher Saving Tips: https://smarterhouse.org/dishwashing/energy-saving-tips
Water Heater Tips: https://www.empirestateplumbing.com/about-us/news-and-events/34156-7-ways-to-make-your-hot-water-heater-more-energy-efficient.html
Devices Information: https://wattsonhomesolutions.com/unplugging-appliances-to-save-energy-myth-the-truth/
