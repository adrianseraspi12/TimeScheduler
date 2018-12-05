<img src="https://lh3.googleusercontent.com/2gw3re6c-WPOz09kHDfX9WBp0EDSHBYw-tGiN9LH6qWsw-Ie4leVVUYisDEhZCHpXArr=s180" alt="logo" width="200px"/>

# TimeScheduler
Time Scheduler is a time management app that lets you schedule, organize, and manage your time.

## Features
1. Create/Edit your schedule.
2. No ads and it's very lightweight
3. Swipe to delete animation
4. Choose different themes

## Screenshots

<img src="https://github.com/adrianseraspi12/TimeScheduler/blob/master/pictures/Screenshot_2018-09-26-21-57-18-25.png" alt="All schedules" width="200px"/> <img src="https://github.com/adrianseraspi12/TimeScheduler/blob/master/pictures/Screenshot_2018-09-26-21-57-38-29.png" alt="Create Schedule" width="200px"/>
<img src="https://github.com/adrianseraspi12/TimeScheduler/blob/master/pictures/Screenshot_2018-09-26-21-57-49-78.png" alt="Choose time" width="200px"/>

Google Playstore = https://play.google.com/store/apps/details?id=com.suzei.timescheduler

## Libraries
- Room Persistence
- Dagger2
- Support
- Butterknife

## Architecture Pattern
MVVM - (Model, View, ViewModel)

<img src="https://github.com/adrianseraspi12/TimeScheduler/blob/master/pictures/mvvm.png" alt="MVVM pic" width="200px"/>

VIEW - UI interaction

VIEWMODEL - Used Asynctask for database change and used LiveData to update everytime there is a change

REPOSITORY - Holds the DAO from the Room

### Issues
1. Not showing alarm/notification for API 23 and up. AlarmManager method that i should use: [setAndAllowWhileIdle](https://developer.android.com/reference/android/app/AlarmManager?hl=pt-br#setAndAllowWhileIdle(int,%20long,%20android.app.PendingIntent))

### References/Teachers
1. https://www.youtube.com/wiseass
2. https://www.youtube.com/channel/UC_Fh8kvtkVPkeihBs42jGcA
3. https://medium.com/@margaretmz/exploring-the-android-architecture-components-117515acfa8
4. https://github.com/googlesamples/android-architecture-components/tree/master/BasicSample

### Note
If there is something wrong with my code please let me know so that I can learn from my mistakes and be a better developer.
