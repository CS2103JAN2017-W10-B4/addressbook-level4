# Test Script


## Intro to the layout
1. The two panels on the left show events as well as tasks (floating and deadlines) <br>
2. Events are automatically sorted by dates, and will be removed automatically after it is over. <br>
3. In the task panel, deadlines are automatically sorted by date and then by priority level. FLoating tasks are always below deadlines. <br>
4. In the bottom of the window, the last updated time is reflected on the left, the current filepath (read and save) is displayed in the middle,
and the bottom right shows the mode that WhatsLeft is in. There are 3 modes. Currently, it is showing Pending activities. Hence, all activities
that are visible now are yet to be over or completed. The other modes are show (all) as well as show completed. It will be demonstrated later. <br>
5. Size of our panels differs from device to device. It displays properly for our group’s devices, but if words are not showing properly, drag events/tasks panel right to
increase visibility. <br>

## Test Script

### 1. Read Sample Data <br>

[read /Users/user/Desktop/main/src/test/data/ManualTesting/SampleData.xml] <br>

expected result : Successfully read WhatsLeft from: /Users/user/Desktop/main/src/test/data/ManualTesting/SampleData.xml <br>


### 2. Save Data Elsewhere <br>

[save /Users/user/Desktop/mytasks.xml] <br>

expected result : Saved WhatsLeft to: /Users/user/Desktop/mytasks.xml <br>


### 3. Add New Event (Jim has a job interview this coming friday) <br>

[add Job Interview sd/friday st/1500 et/1600 l/town] <br>

expected result : New event added: Job Interview Start Time: 15:00 Start Date: 2017-04-14 End Time: 16:00 End Date: 2017-04-14 Location: town Tags: <br>


### 4. Add New Task (Jim needs to prepare his formal attire before friday) <br>

[add prepare formal attire bd/thurs p/high] <br>

expected result : New task(deadline) added: prepare formal attire Priority: high ByDate: 2017-04-13 ByTime: 23:59 Location: null Tags: <br>

### 5. Find Event (Jim gets a call to reschedule his interview, he wants to find the event to edit its details) <br>

[find interview] <br>
[select ev 1] <br>

expected result : 1 activities listed! <br>
Selected Event: Job Interview Start Time: 15:00 Start Date: 2017-04-14 End Time: 16:00 End Date: 2017-04-14 Location: town Tags: <br>

### 6. Edit Event (Jim has found the event, wants to change the timing) <br>

[edit ev 1 st/1600 et/1700] <br>

expected result : Edited Activity with possible clash! : Job Interview Start Time: 16:00 Start Date: 2017-04-14 End Time: 17:00 End Date: 2017-04-14 Location: town Tags: <br>


### 7. Delete Event (Jim realises there could be a clash in events with CS2103 lecture! However he realises there is no more lecture (and good friday) so he wants to delete the lecture event) <br>

[delete ev 9] <br>

expected result : Deleted Event: CS2103 Lecture 13 Start Time: 16:00 Start Date: 2017-04-14 End Time: 18:00 End Date: 2017-04-14 Location: I-CUBE Tags: <br>


### 8. Edit Task (Jim looks at his tasks and realises he has yet to top up his Ez-Link from 2 months back! He wants to edit it to reflect today) <br>

[list] <br>
[edit ts 1 p/high] <br>

expected result : Edited Task: Top-up EZ-link Priority: low ByDate: 2017-02-14 ByTime: 08:00 Location: MRT Tags: [transportation] <br>


### 9. Finish Task (Jim later on goes to finish the task, proceeds to remove it from the task list) <br>

[finish 1] <br>
[refresh] <br>

expected result : Finished task: Top-up EZ-link Priority: high ByDate: 2017-02-14 ByTime: 08:00 Location: MRT Tags: [transportation] <br>
expected result : Calendar showing current week. <br>


### 10. Next (Jim wants to see the calendar view on what he has next week) <br>

[next] <br>
[next 2] <br>

Expected result : Calendar showing 1 week(s) ahead <br>
Expected result : Calendar showing 2 week(s) ahead <br>

### 11. Refresh (Jim wants to jump back to current week) <br>

[refresh] <br>

### 12. Show com (Jim wants to see what he has already completed) <br>

[show com] <br>

Expected result : Successfully changed display preference to show [COMPLETED] tasks <br>

### 13. Redo task (Jim realises his previous top-up of ez-link was not sufficient, he wants to have another of this task without having to type the entire thing again) <br>

[redo 3] <br>

Expected result : Redo task: Top-up EZ-link Priority: high ByDate: 2017-02-14 ByTime: 08:00 Location: MRT Tags: [transportation] <br>

### 14. show  (if Jim wants to view all tasks past and present, he can do so using the show command) <br>

[show] <br>

Expected result : Successfully changed display preference to show [ALL] tasks <br>
Can see uncomplete task marked with exclamation mark. <br>

### 15. Show pend (now jim just wants to view current events and tasks) <br>

[show pend] <br>

Expected result : Successfully changed display preference to show [PENDING] tasks <br>


### 16. Help recur (Suppose jim’s meeting with Ms Glenna is to happen over that 3 days instead of 1, but he forgot the exact syntax) <br>

[help recur] <br>

Expected result : recur: make an event a recurring one. <br>
Parameters: INDEX (must be a positive integer) FREQUENCY (daily or weekly) NUMBER OF TIMES (must be a positive integer) <br>
Example: recur 5 daily 3 <br>


### 17. Recur <br>

[select ev 6] <br>
[recur 6 daily 2] <br>

Expected result : This meet with Ms Glenna Start Time: 13:00 Start Date: 2017-04-11 End Time: 14:00 End Date: 2017-04-11 Location: ICBC Standard Bank Tags:  recurring activity has been added to WhatsLeft <br>


### 18. Edit floating task to deadline <br>

[select ts 17] <br>
[edit ts 17 bd/tmr] <br>

Expected result : Edited Task: Meet Uncle Liang Priority: medium ByDate: 2017-04-10 ByTime: 23:59 Location: Utown Tags: <br>

### 19. Clear event <br>

[clear ev] <br>
[undo] <br>

Expected result : Event list in WhatsLeft has been cleared! <br>
Expected result : undid the previous clear operation <br>

### 20. Clear <br>

[clear] <br>
[undo] <br>

Expected result : WhatsLeft has been cleared! <br>
Expected result : undid the previous clear operation <br>

### 21. Exit <br>

[exit] <br>


## Command Summary

Feature | Command Format | Example |
-------- | :-------- | :--------- |
Show help message | `help [COMMAND]` | `help add`
Add an event | `add DESCRIPTION [st/START_TIME] sd/START_DATE [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...` | `add Industrial Talk st/1600 sd/030717 et/2000 l/FoS`
Add a recurring event | `recur EVENT_INDEX FREQUENCY NUMBER_OF_TIMES` | `recur 2 daily 3`
Add a task | `add DESCRIPTION p/PRIORITY [bt/BY_TIME] [bd/BY_DATE] [l/LOCATION] [ta/TAG]...` | `add Home Assignment 1 bd/tmr l/general office ta/hardcopy`
List | `list` | `list`
Edits an event | `edit ev INDEX [DESCRIPTION] [st/START_TIME] [sd/START_DATE] [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...` | `edit ev 3 Project Discussion et/2300`
Edits a task | `edit ts INDEX [DESCRIPTION] [p/PRIORITY] [bt/BY_TIME] [bd\BYDATE] [l/LOCATION] [ta/TAG]...` | `edit ts 5 bd/next tue`
Find | `find KEYWORD [MORE_KEYWORDS]...` | `find discussion`
Select | `select TYPE INDEX` | `select ev 2`
Delete | `delete TYPE INDEX` | `delete ts 3`
Clear | `clear [TYPE]` | `clear ev`
Finish a task | `finish TASK_INDEX` | `finish 3`
Redo a task | `redo TASK_INDEX` | `show com`<br>`redo 1`
Change status preference | `show [DISPLAY_PREFERENCE]` | `show com`
Undo last operation | `undo` | `undo`
Change storage file location | `save FILEPATH` | `save ./Data/WhatsLeft.xml`
Read data from location | `read FILEPATH` | `read ./MyData/WhatsLeft.xml`
Changes week in calendar | `next [WEEKS_AHEAD]` | `next 2`
Refresh calendar | `refresh` | `refresh`
Exit WhatsLeft | `exit` | `exit`
