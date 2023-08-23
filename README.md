## Picsum Gallery

The architecture that I have used has been **MVVM** and **Jetpack Compose** and for the communication between the ViewModel and the UseCases I used **Flow**.

I have decided to use **Hilt** for the dependency injection as it is powerful and easy to use with the ViewModels.

For the http requests, I used **Retrofit** and **Moshi** to parse the responses as I used them previously and think they are reliable and well maintained.

Also I added **Ktlint** and **Detekt** plugins to check the code style.

<br />
<p align="center">
<img src="https://github.com/ch4vi/PicsumGallery/blob/develop/media/light-mode.gif" width=300px>
&emsp;
<img src="https://github.com/ch4vi/PicsumGallery/blob/develop/media/dark-mode.png" width=300px>
</p>

## Features

### Main features
- Retrieve images from Picsum
- Filtering by Author
- Persist Filters configuration
- Show loading when background tasks
- Error management
- Retry action when error
- Clear filters and list button

### Other features
- Cached images with Room
- Pagination
- Sorting by Image Width and Height
- Sorting ascending or descending
- Animated components (Sorting and Offline banner)
- Offline capabilities
- Unit tests
- Basic UI tests
- Compose

## Future

- Replace **SharedPreferences** by **Proto DataStore**
- Improve UI testing


## Libraries

- **Room** – Database
- **Moshi** – Json parser
- **Retrofit** – Http client
- **Coil** – Image loader and cache
- **Mockito** – Test mocking
- **Hilt** – Dependency injection
- **Ktlint** – Code linter
- **Detekt** – Code analysis
