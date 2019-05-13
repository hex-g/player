package controller


import hive.player.controller.PlayerController
import hive.player.entity.Player
import hive.player.repository.PlayerRepository
import org.mockito.Mock
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
    @Shared Optional<Player> player
    MockMvc mockMvc
    PlayerRepository playerRepository= Mock()
    def setup(){
        when:
        playerRepository.findById()
        then:
        player=playerRepository.findById()
    }
    def "Should add an user"(){
        given:
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController()).build()
        expect:
        mockMvc.perform(get("$urlBase").header(AUTHENTICATED_USER_ID,1)).andDo(print())
       // mockMvc?.perform(get("$urlBase")?.header(AUTHENTICATED_USER_ID,1))?.andExpect(status().isOk())
    }
}
