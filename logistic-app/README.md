```text

  {---------}         {---------}  
 {           }       {           } 
 |[##]----- {---------} -----[##]| 
 |******** {           } ********| 
[|        |[##]-----[##]|        |]
[|____    |*************|    ____|]
 |____}-$[|             |]$-{____| 
 |###|_{_[|____     ____|]_}_|###| 
 [___|____|____}-$-{____|____|___] 
 |___|    |###|_{_}_|###|    |___| 
          [___|_____|___]          
          |___|     |___|          
                                                         
```
# Logistics App

**Logistics App** is a comprehensive Java-based logistics management system designed to streamline delivery operations. It enables the creation, planning, assignment, and tracking of customers, packages, trucks, and delivery routes through a clean and simple command-line interface.




## Features

- **User-friendly CLI:** Manage all logistics operations through intuitive textual commands.
- **Package Management:** Create, assign, and track packages.
- **Truck Operations:** Add trucks, assign them to routes/packages, and view status.
- **Route Planning:** Create and manage delivery routes, assign trucks, and estimate delivery times.
- **Customer Management:** Add customers and view detailed customer info.
- **State Management:** Change the state of packages (e.g., In Transit, Delivered).
- **Data Persistence:** Save and load logistics history.
- **Comprehensive Info Display:** Show all entities or detailed information for each.


## Tech Stack

- **Language:** Java 11+
- **Libraries:** [Jackson](https://github.com/FasterXML/jackson) for JSON parsing


## Installation

Prerequisites

- Java 11 or higher
- IntelliJ IDEA (recommended) or other Java IDE

Install my-project with git clone

```bash
  git clone https://github.com/ivelinyanev/logistic-app.git
  cd logistic-app
```
## Command-Line Interface (CLI) Usage

Interact with the application by typing commands in the console. Each command typically consists of a command name followed by parameters.

### Command Reference

Below are the primary commands supported by LogisticApp, grouped by their functionality:

#### Creation Commands

| Command                  | Description                               | Example Usage                                                     |
|--------------------------|-------------------------------------------|-------------------------------------------------------------------|
| `CREATECUSTOMER`         | Create a new customer                     | `CREATECUSTOMER <firstName> <lastName> <phoneNumber>`                                   |
| `CREATEPACKAGE`          | Create a new delivery package             | `CREATEPACKAGE <senderPhone> <receiverPhone> <weight> <startLocation> <endLocation>` |
| `CREATELOCATION`         | Create a new location                     | `CREATELOCATION <city>`                                           |
| `CREATEROUTE`            | Create a new route                        | `CREATEROUTE <locationId1> <locationId2> ...`                     |
| `CREATETRUCK`            | Create a new truck                        | `CREATETRUCK <model> <capacity> <range>`        |

#### Assignment and State Commands

| Command                  | Description                               | Example Usage                                                     |
|--------------------------|-------------------------------------------|-------------------------------------------------------------------|
| `ASSIGNPACKAGE`          | Assign a package to a truck               | `ASSIGNPACKAGE <packageId> <truckId>`                             |
| `ASSIGNTRUCK`            | Assign a truck to a route                 | `ASSIGNTRUCK <truckId> <routeId>`                                 |
| `CHANGESTATE`            | Change the state of a package             | `CHANGESTATE <packageId> <newState>`                              |

#### Data Persistence

| Command                  | Description                               | Example Usage                                                     |
|--------------------------|-------------------------------------------|-------------------------------------------------------------------|
| `SAVEHISTORY`            | Save the current state/history (happens automatically)           | `SAVEHISTORY `                                          |
| `LOADHISTORY`            | Load state/history from a file   (happens automatically)         | `LOADHISTORY `                                          |

#### Information and Display Commands

| Command                      | Description                           | Example Usage                                                     |
|------------------------------|---------------------------------------|-------------------------------------------------------------------|
| `SHOWCUSTOMERINFO`           | Show detailed info for a customer     | `SHOWCUSTOMERINFO <phoneNumber> <transferType>`                                   |
| `SHOWDELIVERYPACKAGEINFO`    | Show info for a delivery package      | `SHOWDELIVERYPACKAGEINFO <packageId>`                             |
| `SHOWTRUCKINFO`              | Show info for a truck                 | `SHOWTRUCKINFO <truckId>`                                         |
| `SHOWROUTEINFO`              | Show info for a route                 | `SHOWROUTEINFO <routeId>`                                         |
| `SHOWALLCUSTOMERS`           | List all customers                    | `SHOWALLCUSTOMERS`                                                |
| `SHOWALLDELIVERYPACKAGES`    | List all delivery packages            | `SHOWALLDELIVERYPACKAGES`                                         |
| `SHOWALLTRUCKS`              | List all trucks                       | `SHOWALLTRUCKS`                                                   |
| `SHOWALLROUTES`              | List all routes                       | `SHOWALLROUTES`                                                   |

## 📁 Project Structure

```text
.  
├── data/                  # Persistent JSON storage  
├── lib/                   # Third-party dependencies (Jackson, JUnit)  
├── src/  
│   └── logisticsapp/  
│       ├── Startup.java           # App entry point  
│       ├── commands/              # All CLI commands  
│       │   ├── assign/            # Assign trucks & packages  
│       │   ├── create/            # Create customers, trucks, etc.  
│       │   ├── enums/             # CommandType + TransferType  
│       │   ├── saveload/          # Save/load to JSON  
│       │   └── show/              # Show info commands
│       │   └── changestate/       # Change state of packages
│       ├── core/                  # Engine, services, repositories  
│       ├── models/                # Domain models + DTOs  
│       └── utils/                 # Helpers + Mappers  
├── tests/                         # JUnit tests  
└── README.md                      # You’re reading it!  
```
## Authors

- [@todorkrushkov](https://github.com/todorkrushkov)

- [@ivelinyanev](https://github.com/ivelinyanev)

- [@Binev7](https://github.com/ivelinyanev)
## License

This project is under the [MIT License](https://choosealicense.com/licenses/mit/).


## Contributing

Contributions are always welcome!

See `contributing.md` for ways to get started.

Please adhere to this project's `code of conduct`.


## Feedback

If you have any feedback, please reach out to us at ivelinyanev00@gmail.com

