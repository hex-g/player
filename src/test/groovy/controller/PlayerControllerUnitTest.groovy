package controller

import hive.player.controller.PlayerController
import hive.player.entity.Player
import hive.player.entity.PlayerOptions
import hive.player.entity.PlayerSocial
import hive.player.repository.PlayerRepository
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static hive.pandora.constant.HiveInternalHeaders.AUTHENTICATED_USER_ID
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

class PlayerControllerUnitTest extends Specification {

    def urlBase = 'http://localhost:9600/'
    @Shared Player player
    MockMvc mockMvc
    PlayerRepository playerRepository= Mock()
    def invalidProfileDataJson='{"invalid_key":"value"}'
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
        "darkmode": "on",
        "notify_hiveshare": "on",
        "notify_hivecentral": "on",
        "notify_disciplines": "on"
    },
    "social": {
        "github": "git",
        "linkedIn": "linkedin",
        "twitter": "@twitter"
    }
}
                        '''
    def setup(){
    }

    def "Should GET profile data successfully"(){

        given:"Correct header"
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()

        when:"Success when GET"
        def response=mockMvc.perform(get("$urlBase")
                .header(AUTHENTICATED_USER_ID,val))
                .andReturn()
                .getResponse()
        then:
        response.getStatus()==200

        where:"numbers or string as header value"
        val | _
        1   | _
        "2" | _

    }

    def "Should POST profile data successfully"(){

        given:"Correct header"
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()

        when:"Success when POST"
        def response=mockMvc.perform(post("$urlBase")
                .header(AUTHENTICATED_USER_ID,val)
                .contentType('application/json')
                .content(validProfileDataJson))
                .andReturn().getResponse()
        then:
        response.getStatus()==200

        where:"numbers or string as header value"
        val | _
        1   | _
        "2" | _

    }
    def "Should AAAAA when GET"(){

        given:
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()

        when:
        def response=mockMvc.perform(get("$urlBase")
                .header(key,val)).andReturn().getResponse()
        println response.getStatus()

        then:
        response.getStatus()==400

        where:" wrong key on header with blank or filled value"
        key                     | val        | _
        "wrong key"             | "something"| _
        "wrong_key"             | " "        | _

    }
    def "Should AAAAA when POST"(){

        given:
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()

        when:
        def response=mockMvc.perform(get("$urlBase")
                .header(key,val)).andReturn().getResponse()
        println response.getStatus()

        then:""
        response.getStatus()==400

        where:" wrong key on header with blank or filled value"
        key                     | val        | _
        "wrong key"             | "something"| _
        "wrong_key"             | " "        | _

    }

    def "Should give illegal argument GET profile data"(){

        given:"Wrong header"
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()

        when:"GET with header key as #key"
        mockMvc.perform(get("$urlBase")
                .header(key,val)).andReturn()

        then:
        thrown IllegalArgumentException


        where:"empty or null key"
        key                     | val        | _
        AUTHENTICATED_USER_ID   | null       | _
        null                    | "something"| _
        ""                      | "something"| _
        ""                      | null       | _

    }

    def "Should give illegal argument POST profile data"(){

        given:"Wrong header"
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()

        when:"POST with header key as #key"
        mockMvc.perform(post("$urlBase")
                .header(key,1)
                .contentType('application/json')
                .content(validProfileDataJson)).andReturn()

        then:
        thrown IllegalArgumentException

        where:"empty or null key"
        key | _
        ""  | _
        null| _

    }
}
