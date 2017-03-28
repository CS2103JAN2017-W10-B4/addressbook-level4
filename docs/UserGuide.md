# WhatsLeft - User Guide

By : `Team CS2103JAN2017-W10-B4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Feb 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `whatsleft.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your WhatsLeft.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all events and tasks.
   * **`add`**` read CS2103 handout p/high` :
     adds a floating task with description read CS2103 handout and priority high to WhatsLeft.
   * **`delete ev`**` 3` : deletes the 3rd event shown in the current event list.
   * **`exit`** : exits the app.
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help [COMMAND]`

> * Command is optional.
> * When there is no command specified, a help window with all commands will be shown.
> * When the command is specifies, the format and examples of the command will be shown.
> * Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding an event/task : `add`

#### 2.2.1. Adding an event : `add`

Adds an event to the event list.<br>
Format: `add DESCRIPTION [st/START_TIME] sd/START_DATE [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...`

> * Events must be added with description.
> * Start date is required, while other fields are optional.
> * Start date and end date should have format `DDMMYY`, e.g. `230117`
> * Start time and end time should have format `MMHH`, e.g. `2359`
> * Default value for end date is set to be the same as start date
> * Default value for start time is set to be 00:01
> * Default value for end time is set to be 23:59
> * Tags can contain only one word without space.
> * Events can have any number of tags (including 0).
> * If description or start date is not entered, help message for add will appear.

Examples:

* `add PhotoShop Workshop st/1900 sd/130217 ed/130217 l/CLB ta/take laptop`
* `add Industrial Talk st/1800 sd/030517 et/2000 ed/030517 l/FoS`

#### 2.2.2. Adding a task : `add`

Adds a task to the task list.<br>
Tasks have completion status, which is set to [pending] by default. <br>
User can mark a task as [Completed]. (See section 2.13) <br>
Format: `add DESCRIPTION p/PRIORITY [l/LOCATION] [ta/TAG]...`

> * Tasks must be added with description and priority.
> * Priority can only take on the values `high`. `mediu.` or `low`.
> * Tags can contain only one word without space.
> * Other fields are optional.
> * Tasks can have any number of tags (including 0).
> * If description or priority is not entered, help message for add will appear.

Examples:

* `add Review CS2103 p/high ta/review`
* `add Buy groceries p/medium`

#### 2.2.3. Adding a deadline : `add`

Adds a deadline equivalent to adding a task with by time and by date.<br>
Deadlines have completion status, which is set to [pending] by default. <br>
User can mark a deadline as [Completed]. (See section 2.13) <br>
Format: `add DESCRIPTION p/PRIORITY [bt/BYTIME] bd/BY_DATE [l/LOCATION] [ta/TAG]...`

> * Deadlines must be added with description and priority.
> * By date and By time is to be specified to make it a deadline, which would otherwise remains a task.
> * By date should have format `DDMMYY`, e.g. `230117`
> * End time should have format `MMHH`, e.g. `2359`
> * If only by time is specified, by date would be set as the current day by default.
> * If only by date is specified, by time would be set as 23:59 by default.
> * Tags can contain only one word without space.
> * Deadlines can have any number of tags (including 0).
> * If description is not entered, help message for add will appear.

Examples:

* `add Home Assignment 1 p/high bd/210317 l/general office ta/hardcopy`
* `add Project Report p/medium bt/2300 bd/120417 ta/softcopy`

### 2.3. Listing all events/tasks : `list`

Shows the lists of all events and tasks in WhatsLeft.<br>
Format: `list [TYPE]`

> * Type is optional.
> * If type is not specified, 2 separated lists of all events and tasks (including deadlines) will appear.
> * Type should be `ev` or `ts`.

Examples:

* `list` <br>
  Shows 2 separated lists of all events and tasks in WhatsLeft.

* `list ev`<br>
  Shows the list of all events in WhatsLeft.

### 2.4. Editing an event/task : `edit`

#### 2.4.1. Editing an event : `edit ev`

Edits an existing event in WhatsLeft. Undoable.<br>
Format: `edit ev INDEX [DESCRIPTION] [st/START_TIME] [sd/START_DATE] [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...`

> * Edits the event at the specified `INDEX`.
    The index refers to the index number shown in the last event list.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the event will be removed i.e adding of tags is not cumulative.
> * You can remove start time, end time, end date, tags or location by typing `st/`, `et/`, `ed/`, `l/` or `ta/` without specifying any content after it.

Examples:

* `edit ev 2 st/0900 et/1300`<br>
  Edits the start time and end time of the 2nd event to be `09:00` and `13:00` respectively.

* `edit ev 3 Project Discussion ta/`<br>
  Edits the description of the 3rd event to be `Project Discussion` and clears all tags.

#### 2.4.2. Editing a task : `edit ts`

Edits an existing task in WhatsLeft. Undoable.<br>
Format: `edit ts INDEX [DESCRIPTION] [p/PRIORITY] [bt/BY_TIME] [bd\BYDATE] [l/LOCATION] [ta/TAG]...`

> * Edits the task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove by time, by date, location or tags by typing `bt/`, `bd/`, `l` or `ta/` without specifying any content after it.

Examples:

* `edit ts 5 l/discussion room 3`<br>
  Edits the location of the 5th task to be `discussion room 3`.

* `edit ts 3 p/high ta/`<br>
  Edits the priority of the 3rd task to be high and clears the tags.

### 2.5. Finding all events/tasks containing any keyword in their description : `find`

Finds events/tasks whose descriptions contain any of the given keywords.<br>
Format: `find [TYPE] KEYWORD [MORE_KEYWORDS]`

> * Type is optional.
> * If type is not specified, 2 separated lists of matched events and tasks will appear.
> * Type should be `ev` or `ts`.
> * The search is case insensitive. e.g `discussion` will match `Discussion`
> * The order of the keywords does not matter. e.g. `Project Discussion` will match `Discussion Project`
> * Only the description is searched.
> * Events/tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Project` will match `Project Discussion`

Examples:

* `find project discussion`<br>
  Returns 2 separated lists of events and tasks having description `project` or `discussion`.

* `find ts survey`<br>
  Returns a task list of tasks having description `survey`.


### 2.6. Selecting an event/task : `select`

Views the specified event/task form the event/task list.

Format: `view TYPE INDEX`

> * Type should be `ev` or `ts`.
> * Selects the event/task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent event/task listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select ev 2`<br>
  Selects the 2nd event in the event list.

### 2.7. Deleting an event/task : `delete`

Deletes the specified event/task from WhatsLeft. Undoable.<br>
Format: `delete TYPE INDEX`

> * Type should be `ev` or `ts`.
> * Deletes the event/task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent event/task listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete ev 2`<br>
  Deletes the 2nd event in the event list.
* `find exam`<br>
  `delete ev 1`<br>
  Deletes the 1st event in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all events/tasks in WhatsLeft. Undoable.<br>
Format: `clear [TYPE]`

> * Type is optional.
> * If type is not specified, all events and tasks in WhatsLeft will be removed.
> * Type should be `ev` or `ts`.

Examples:

* `clear` <br>
  Removes all events, deadlines and tasks in WhatsLeft.

* `clear ev`<br>
  Removes all events in WhatsLeft.

### 2.9. Finishing a task: `finish`

Finishes the specified task/deadline from WhatsLeft. Undoable.<br>
Format: `finish INDEX`

> * Finishes the task/event at the specified `INDEX`.
> * The index refers to the index number shown in the most recent task listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `finish 2`<br>
  Deletes the 2nd event in the event list.
* `find Assignment`<br>
  `delete 1`<br>
  Deletes the 1st event in the results of the `find` command.

### 2.10. Task display preference: `show`

  Sets the display preference of tasks in three categories.<br>
  User is able to view all tasks, completed tasks or pending tasks.
  Format: `show [DISPLAY_PREFERENCE]`

  > * DISPLAY_PREFERENCE should be empty, `com` or `pend`,
  > * representing all tasks, completed tasks and pending tasks respectively.

Examples:

* `show`<br>
* `show com`<br>
* `show pend`<br>

### 2.11. Undoing the latest command : `undo`

Undoes the latest command.<br>
Format: `undo`

> * Undoes immediately after `edit`, `delete`, `finish` and `clear` commands.
> * Cannot undo more than once consecutively.

Examples:

* `finish ts 4`<br>
  `undo`<br>
  Undoes finishing task 4.

### 2.12. Redoing the previous undo command : `redo`

Redoes the previous undo command.<br>
Format: `redo`

> * Recovers if the latest command is `undo`.
> * Cannot redo more than once consecutively.

Examples:

* `edit ts 2 bd/230417`<br>
  `undo`<br>
  `redo`<br>
  Reverses the previous undo command.

### 2.13. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.14. Saving the data

WhatsLeft data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous WhatsLeft folder.

## 4. Command Summary

* **Help** : `help` <br>

* **Add Event** : `add DESCRIPTION [st/START_TIME] sd/START_DATE [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...`<br>
  e.g. `add Industrial Talk st/1800 sd/030517 et/2000 l/FoS`
  
* **Add Task** : `add DESCRIPTION p/PRIORITY [l/LOCATION] [ta/TAG]...`<br>
  e.g. `add Buy groceries p/medium l/FairPrice`
  
* **Add Deadline** : `add DESCRIPTION p/PRIORITY [bt/BY_TIME] [bd/BY_DATE] [l/LOCATION] [ta/TAG]...`<br>
  e.g. `add Home Assignment 1 bd/210317 l/general office ta/hardcopy`

* **List** : `list [TYPE]`<br>
  e.g. `list ev`

* **Edit Event** : `edit ev INDEX [DESCRIPTION] [st/START_TIME] [sd/START_DATE] [et/END_TIME] [ed/END_DATE] [l/LOCATION] [ta/TAG]...`<br>
  e.g. `edit ev 3 Project Discussion ta/`

* **Edit Task** : `edit ts INDEX [DESCRIPTION] [p/PRIORITY] [bt/BY_TIME] [bd\BYDATE] [l/LOCATION] [ta/TAG]...`<br>
  e.g. `edit ts 5 l/discussion room 3`

* **Find** : `find [TYPE] KEYWORD [MORE_KEYWORDS]`<br>
  e.g. `find exam`

* **Select** : `select TYPE INDEX`<br>
  e.g. `view ev 2`

* **Delete** : `delete TYPE INDEX`<br>
  e.g. `delete ts 3`

* **Clear** : `clear [TYPE]`<br>
  e.g. `clear ev`

* **Finish Task** : `finish INDEX`<br>
  e.g. `finish 2`
  
* **Show Tasks** : `show [DISPLAY_PREFERENCE]`<br>
  e.g. `show com`

* **Undo** : `undo`<br>

* **Redo** : `redo`<br>

* **Exit** : `exit`<br>
