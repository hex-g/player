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

        expect:"Success when GET"
        mockMvc.perform(get("$urlBase")
                .header(AUTHENTICATED_USER_ID,val))
                .andExpect(status().isOk())

        where:"numbers or string as header value"
        val | _
        1   | _
        "2" | _

    }

    def "Should POST profile data successfully"(){

        given:"Correct header"
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()

        expect:"Success when POST"
        mockMvc.perform(post("$urlBase")
                .header(AUTHENTICATED_USER_ID,val)
                .contentType('application/json')
                .content(validProfileDataJson))
                .andExpect(status().isOk())

        where:"numbers or string as header value"
        val | _
        1   | _
        "2" | _
    }
    def "Should not accept GET profile data"(){

        given:"Wrong header"
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()

        when:"status NotAcceptable when GET"
        def response=mockMvc.perform(get("$urlBase")
                .header(key,1))
                .andExpect(status().isNotAcceptable())
        println response

        then:
//        response.andExpect(status().is(400))
        thrown IllegalArgumentException

        where:"Wrong or empty key"
        key | _
        ""  | _
        " " | _
        null| _

    }

    def "Should not accept POST profile data"(){

        given:"Wrong header"
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerRepository)).build()

        when:"status NotAcceptable when POST"
        def response=mockMvc.perform(post("$urlBase")
                .header(key,1)
                .contentType('application/json')
                .content(validProfileDataJson))
        println response

        then:
//        response.andExpect(status().is(400))
        thrown IllegalArgumentException

        where:"Wrong or empty key"
        key | _
        ""  | _
        " " | _
        null| _

    }
}
