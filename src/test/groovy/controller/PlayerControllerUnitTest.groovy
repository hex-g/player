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
    def setup(){
        player=new Player(
                1,
                "alias",
                "email",
                "11985054202",
                "String grande",
                "21/04/1998",
                "46358570855",
                new PlayerOptions(
                        "ouro",
                        "titan do café",
                        "on",
                        "on",
                        "on",
                        "on"),
                new PlayerSocial("git","linkedin","@twitter"))
    }
    def "Should add an user"(){
        given:
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController()).build()
        and:
        playerRepository.findById(1) >> player
        expect:
//        mockMvc?.perform(get("$urlBase/create")).andDo(print())
        mockMvc?.perform(get("$urlBase").header(AUTHENTICATED_USER_ID,1)).andDo(print())
       // mockMvc?.perform(get("$urlBase")?.header(AUTHENTICATED_USER_ID,1))?.andExpect(status().isOk())
    }
}
