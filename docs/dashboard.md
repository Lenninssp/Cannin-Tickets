# Dashboard

### Start application

```mermaid
flowchart TD
  start([start])
  start --> AA[check logged in user]
  AA --> AB{is user logged in}
  AB -- no --> ABerr[redirect user to login page]
  AB -- yes --> AC[["<a href="#fetch-events">fetches events()</a>"]]

  AC --> A[checks user role]
  A --> B{ is user a seller?}
  B -- no  --> BA[redirect to Buyer dashboard] --> BB[[" <a href="#management-view">management_view()</a>"]]
  B -- yes --> BC[redirect to seller dashboard] --> BD[["<a href="#marketplace-view">marketplace_view()</a>"]]

```
 
### Management View
```mermaid
flowchart TD
  subgraph management_view[Management view]
  direction TB
  start((start))-->A
  A[["<a href="#fetch-events">fetch my events()</a>"]]
  A --> B{events are empty}
  B -- yes --> Berr[display UI saying that there are no available events]
  B -- no --> C[Go through events and display them]
  Berr & C --> D["user makes click on 'create event'"]
  C --> E["user makes click on "modify event""]
  D --> Df[[<a href="">create event view</a>]]
  E --> Ef[[<a href="">buyer event view</a>]]


  end

```

### Marketplace view
```mermaid
flowchart TD
  subgraph marketplace_view [Marketplace View]
  start((start))-->A
  A[["<a href="#get-upcoming-events">get_upcoming_events()</a>"]]
  A --> B{events are empty}
  B -- yes --> Berr[display UI saying that there are no available events]
  B -- no --> C[Go through events and display them]
  C --> C1[user clicks event] & C2[user likes event]
  direction TB

  end

```

## EventList Class 

### Fetch events
```mermaid
flowchart TD
  subgraph fetch_events
  A["EventList.populate()"]
  A --> B["<a href="#get-events">GET /api/events (JWT attached)</a>"]
  B --> C{response ok?}
  C -- yes --> D[parse events and store in EventList]
  C -- no --> E[show error message]
  end

```

### get upcoming events
```mermaid
flowchart TD
  subgraph get_upcoming_events[get upcoming events]
  direction TB
  A[gets called]
  A --> B[checks saved events]
  B --> C{are there any existent events?}
  C -- no --> Cerr[Error: there are no loaded events, please properly mount the events of create new events]
  C -- yes --> D[get current time]
  D --> E[Create an empty list to hold the events]
  E-->F[iterate through events]
  F --> FA{is event in position null?}
  FA -- no --> G{is event.Date > currentDate && event.status == Public}
  G -- no --> Gno[continue or finish] --> FA
  G -- yes --> Gyes[add event to upcoming events list] --> Gno
  FA -- yes --> H[return new list of events]

  end

```

### get seller events
```mermaid
flowchart TD
  subgraph get_seller_events [EventsList.getSellerEvents]
  direction TB

  A["Function called: getSellerEvents()"]
  A --> B{events list empty?}
  B -- yes --> B_err[return error or empty result]
  B -- no --> C["Initialize: published = [], private = []"]

  C --> D[Loop through events list]
  D --> E{event.public == True}
  
  E -- yes --> F[Add to published list] --> D_continue[Next]
  E -- no --> G[Add to private list] --> D_continue[Next]

  D_continue --> D
  D --> H{loop finished?}
  
  H -- yes --> I["return { published, private }"]
end
```

### 
```mermaid
flowchart TD
  subgraph X
  direction TB

  end

```

### 
```mermaid
flowchart TD
  subgraph X
  direction TB

  end

```


### 
```mermaid
flowchart TD
  subgraph X
  direction TB

  end

```


### 
```mermaid
flowchart TD
  subgraph X
  direction TB

  end
```

## SERVER

### GET EVENTS
> GET /api/events/public-events

```mermaid
flowchart TD
    subgraph get_events_http
    direction TB

    CA["Client --> GET /api/events (Bearer JWT)"]
    CA --> CB{Backend receives request}
    CB -- no --> CBerr[ERROR 500: failed connection with server]
    CB -- yes --> CD[Extract JWT from Authorization header]
    CD --> CF[[Verify JWT signature]]

    CF -- invalid --> CE[401 Unauthorized]

    CF -- valid --> CG[Extract userId from JWT]

    CG --> DB["SQL:
      SELECT role
      FROM users
      WHERE id = :userId"]

    DB --> DC{role == 'SELLER' ?}
    DC -- yes --> DS["SQL:
        SELECT *
        FROM events
        WHERE organizerId = :userId"]
    DS --> DE["Return 200 + all events (draft/active/canceled/finished)"]
    DC -- no --> DBI["SQL:
        SELECT *
        FROM events
        WHERE status = 'ACTIVE' 
        AND public = true
        ORDER BY eventDate ASC"]
    DBI --> DBR[Return 200 + only ACTIVE events]
    end
```
