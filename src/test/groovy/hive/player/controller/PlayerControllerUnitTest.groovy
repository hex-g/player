package hive.player.controller


import hive.player.entity.Player
import hive.player.entity.PlayerOptions
import hive.player.repository.PlayerRepository
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static hive.pandora.constant.HiveInternalHeaders.AUTHENTICATED_USER_ID

class PlayerControllerUnitTest extends Specification {

    def urlBase = 'http://localhost:9600/'
    @Shared Player player
    MockMvc mockMvc
    PlayerRepository playerRepository = Mock()

    def validProfileDataJson = '''
    {
        "loginAlias": "custom2",
        "email": "email",
        "telnumber": "11985055502",
        "flavorText": "String grande Lorem Ipsum dolor aquicumsitum amet",
        "birthday": "21/04/1998",
        "options": {
            "laurel_wreath": "ouro",
            "honorific": "titan do café",
            "darkmode": "true",
            "notify_hiveshare": "true",
            "notify_hivecentral": "true",
            "notify_disciplines": "true"
        },
        "social": {
            "github": "git",
            "linkedIn": "linkedin",
            "twitter": "@twitter"
        }
    }
                        '''
    def setup(){
        createAnPlayer()
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()
    }

    def createAnPlayer(){
        def options = new PlayerOptions(
                "gold",
                "Coffee Titan",
                true,
                true,
                true,
                true)
        player = new Player(
                "CompletePlayer",
                "alias",
                "email",
                "11985054202",
                "Some flavor text",
                "21/04/1998",
                options,
                null)
    }

    def "Should return BAD REQUEST when POST a Json with playerId"(){

        given:"a JSON containing playerId"
        def invalidProfileDataJson='{"playerId":10}'

        when:"perform POST"
        def response =
                mockMvc.perform(post("$urlBase")
                .header(AUTHENTICATED_USER_ID,1)
                .contentType('application/json')
                .content(invalidProfileDataJson))
                .andReturn()
                .getResponse()

        then:"HttpResponse as BAD REQUEST"
        response.getStatus()==400
        response.getErrorMessage()=="The autogenerated playerId should not be in JSON"

    }

    def '''
        Given profile options with valid values in boolean fields,
        when perform POST,
        then HttpResponse as BAD REQUEST.
        '''(){

        given:
        def validProfileOptionsJson= $/
        {
            "options": {
                "laurel_wreath": "ouro",
                "honorific": "titan do café",
                "darkmode": $validBooleanValues,
                "notify_hiveshare": $validBooleanValues,
                "notify_hivecentral": $validBooleanValues,
                "notify_disciplines": $validBooleanValues
            }
        }
        /$

        when:
        def response =
                mockMvc.perform(post("$urlBase")
                        .header(AUTHENTICATED_USER_ID,1)
                        .contentType('application/json')
                        .content(validProfileOptionsJson))
                        .andReturn()
                        .getResponse()

        then:
        response.getStatus()==200

        where:
        validBooleanValues << ['\"True\"', '\"true\"', true, 1, '\"False\"', '\"false\"', false, 0]
    }

    def '''
        Given profile options with string values in boolean fields,
        when perform POST,
        then HttpResponse as BAD REQUEST.
        '''(){

        given:
        def invalidProfileOptionsJson= $/
        {
            "options": {
                "laurel_wreath": "ouro",
                "honorific": "titan do café",
                "darkmode": $testedStringValues,
                "notify_hiveshare": $testedStringValues,
                "notify_hivecentral": $testedStringValues,
                "notify_disciplines": $testedStringValues
            }
        }
        /$

        when:
        def response =
                mockMvc.perform(post("$urlBase")
                        .header(AUTHENTICATED_USER_ID,1)
                        .contentType('application/json')
                        .content(invalidProfileOptionsJson))
                        .andReturn()
                        .getResponse()
        then:
        response.getStatus() == 400

        where:
        testedStringValues << ['\"TrUe\"', '\"FALSE\"', '\"Something\"']
    }

    def '''
        Given correct header key,
        when perform GET,
        then HttpResponse as OK.
        Where header value accepts numbers or string to query.
        '''(){

        given:
        def headerKey=AUTHENTICATED_USER_ID

        when:
        def response=mockMvc.perform(get("$urlBase")
                .header(headerKey,val))
                .andReturn()
                .getResponse()

        then:
        response.getStatus()==200

        where:
        val | _
        1   | _
        "2" | _

    }

    def '''
        Given correct header key,
        when perform POST with valid json,
        then HttpResponse as OK.
        Where header value accepts numbers or string to query.
        '''(){

        given:
        def headerKey=AUTHENTICATED_USER_ID

        when:
        def response=mockMvc.perform(post("$urlBase")
                .header(headerKey,val)
                .contentType('application/json')
                .content(validProfileDataJson))
                .andReturn().getResponse()

        then:
        response.getStatus()==200

        where:
        val | _
        1   | _
        "2" | _

    }
}
