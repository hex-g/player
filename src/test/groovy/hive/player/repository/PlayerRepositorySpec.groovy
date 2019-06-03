package hive.player.repository

import hive.player.entity.Player
import hive.player.entity.PlayerOptions
import hive.player.entity.PlayerSocial

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

import spock.lang.Shared
import spock.lang.Specification

@DataJpaTest
class PlayerRepositorySpec extends Specification {

    @Shared
    Player player
    @Shared
    def existentAuthIdList = []
    @Shared
    private PlayerRepository playerRepository

    def createACompletePlayer(){
        def social = new PlayerSocial()
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
                social)
        return player
    }

    def createEmptyPlayers(int quantity){
        quantity.times {
            playerRepository.save new Player(it.toString())
            existentAuthIdList+=it.toString()
        }
    }
    @Autowired(required = true)
    def initRepo(PlayerRepository playerRepository){
        this.playerRepository=playerRepository
    }

    def setupSpec(){
        initRepo(playerRepository)
    }

    def setup() {
        createEmptyPlayers 1
    }

    def cleanup() {
        playerRepository.deleteAll()
    }

    def '''
        Given an player with complete data,
        when create this player,
        then all player information should have persisted.
        '''() {

        given:
        def playerToPersist = createACompletePlayer()
        def authenticatedId = playerToPersist.getAuthenticatedUserId()

        when:
        playerRepository.save playerToPersist
        def persistedPlayer = playerRepository.findByAuthenticatedUserId authenticatedId

        then:
        player == persistedPlayer
    }

    def '''
        Given existent authenticatedUserId,
        when search in database,
        then the player exists.
        '''() {

        given:
        def authenticatedId = existentAuthenticatedId

        when:
        def persistedPlayer = playerRepository.findByAuthenticatedUserId authenticatedId

        then:
        persistedPlayer != null

        where:
        existentAuthenticatedId << existentAuthIdList
    }

    def '''
        Given not existent authenticatedUserId,
        when search in database,
        then the player not exists.
        '''() {

        given:
        def authenticatedId = "-1"

        when:
        def persistedPlayer = playerRepository.findByAuthenticatedUserId authenticatedId

        then:
        persistedPlayer == null
    }


    def '''
        Given existent authenticatedUserId,
        when search in database,
        then the found player have a not null playerId.
        '''() {

        given:
        def authenticatedId = existentAuthenticatedId

        when:
        def persistedPlayer = playerRepository.findByAuthenticatedUserId authenticatedId

        then:
        persistedPlayer.getPlayerId() != null

        where:
        existentAuthenticatedId << existentAuthIdList
    }
}
