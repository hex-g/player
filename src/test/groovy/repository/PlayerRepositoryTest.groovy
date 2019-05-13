package repository

import hive.player.entity.Player
import hive.player.entity.PlayerOptions
import hive.player.entity.PlayerSocial
import hive.player.repository.PlayerRepository
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Shared
import spock.lang.Specification

class PlayerRepositoryTest extends Specification{
    @Shared
    Player player

    @Shared
    PlayerRepository playerRepository

    def setup(){
        def social=new PlayerSocial()
        def options = new PlayerOptions(
                "ouro",
                "titan do café",
                "on",
                "on",
                "on",
                "on")
        player=new Player(
                1,
                "alias",
                "email",
                "11985054202",
                "String grande",
                "21/04/1998",
                "46358570855",
                options,
                social)
        playerRepository.save(player)
    }

    def 'Test User Creation'(){
        given:'dado'
        def mina='Jennifer'
        def persistedPlayer=playerRepository.getOne 1
        when:'quando'
        def frase="O nome dela é $mina"
        then:'então'
        player==persistedPlayer
    }
}
