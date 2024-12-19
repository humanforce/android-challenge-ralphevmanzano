# Humanforce Android Engineering Challenge

## Overview
This challenge is designed to assess your Android engineering skills through the development of a simple weather forecast application. We're looking for innovative solutions that demonstrate strong architectural thinking, clean code principles, an eye for design,and a deep understanding of Android development best practices.

## Project Objective
Implement the requirements below to create a weather forecast application using the OpenWeatherMap API to showcase your ability to design scalable, maintainable, and high-quality Android software.

## API Endpoints
| Endpoint | Remarks |
|---|-----|
| [5 day weather forecast](https://openweathermap.org/forecast5) | Note the 3 hour time period in forecast data, this must be aggregated to get daily min/max values. |
| [GeoCoding API](https://openweathermap.org/api/geocoding-api) | Fetch coordinates for a text based search term to use in forecast/current weather API calls. |
| [Current Weather](https://openweathermap.org/current) | Fetch current conditions for the given coordinates.

# Requirements

> **Important**: Pay close attention to the requirement to aggregate 3 hour forecast windows in the 5 day forecast data to get daily min/max forecast values.

### Location-Based Weather
- Automatically fetch and display current weather for the user's current location
- Handle location services unavailability gracefully
- Provide 5-day forecast for current location

### Search and Location Management
- Implement city search functionality
  - Allow searching locations by city name
  - Display search results
  - Enable selecting a search result to view its weather
  - Show 5-day forecast for selected location

### Saved Locations
- Save favorite locations
- Display a list of saved locations
- Allow switching between saved locations

### Weather Display
- Show weather in metric units
- Use weather condition icons
  - Use provided OpenWeatherMap icons.

## Error Handling
- Gracefully manage:
  - Network connectivity issues
  - API errors
  - Location service restrictions


## Technical Constraints
- Use Kotlin & Jetpack Compose
- Implement robust error management
- Create intuitive, responsive UI

## API Configuration

### OpenWeatherMap API Setup

#### 1. Generate an OpenWeatherMap API Key
1. Visit [OpenWeatherMap](https://openweathermap.org/api)
2. Sign up for a free account
3. Generate an API key from your account dashboard

#### 2. Configure the API Key
**IMPORTANT: Never commit API keys directly to source control**

1. In the project root directory, run `cp .env.example .env`.
2. Add your OpenWeatherMap API key to the copied `.env` file.
3. Build the project in Android Studio to inject the API key defined the `.env` file to the apps `BuildConfig`.

#### 3. Consumming the API Key
An `APIKeyManager` helper object is provided for you to access the API key. If the previous two steps are followed then the API key can simply be accessed via `APIKeyManager.apiKey` anywhere in the project.

## Bonus Challenges
- Implement offline caching
- Support multiple temperature units
- Implement widget extension

## Submission Guidelines
1. Provide any additional setup instructions
2. Include comments explaining complex logic
3. Submit code that runs without additional configuration

## Time Expectation
- Estimated completion time: 4-6 hours
- Focus on quality over complete feature set

## Questions?
If you have any questions or need any clarifications, please reach out to your hiring manager or recruiter.

**Good luck!**

