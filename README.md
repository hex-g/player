# player
---
**The profile data API**

*"A person in its life is as a player."*

### First steps
(Configuring the environment):

1. git clone https://github.com/hex-g/player.git
    1. cd player
2. git checkout [branch]
3. git submodule init
4. git submodule update 
5. Run the project in your IDE
---
### Usage
> URL: `http://localhost:9600/`

#### ![#c5f015](https://placehold.it/15/c5f015/000000?text=+) `GET`
If an request profile data does not exist then a new one be created. 
* `header`
    * *key*: `authenticated-user-id`
    * *value*: [string]
    
#### ![#1589F0](https://placehold.it/15/1589F0/000000?text=+) `POST`
Inserts a new profile data or updates an existent.
* `header`
    * *key*: `authenticated-user-id`
    * *value*: [string]
* `body JSON`
    * *value*: 
    ```
    {
            "loginAlias": string,
            "email": string,
            "telnumber": string,
            "flavorText": string,
            "birthday": string,
            "options": {
                "laurel_wreath": string,
                "honorific": string,
                "darkmode": string["on"/"off"],
                "notify_hiveshare": string["on"/"off"],
                "notify_hivecentral": string["on"/"off"],
                "notify_disciplines": string["on"/"off"]
            },
            "social": {
                "github": string,
                "linkedIn": string,
                "twitter": string
            }
        }
    ```
    

