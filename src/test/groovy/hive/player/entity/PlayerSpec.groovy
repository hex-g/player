package hive.player.entity

import hive.player.exception.BlankIdException
import spock.lang.Specification

class PlayerSpec extends Specification{

    def "Should not accept blank value as authenticated user id in constructor"(){
        when:
        new Player(blankValues)

        then:
        thrown BlankIdException

        where:
        blankValues<<[""," "]
    }

    def "Should not set blank value into authenticated user Id"(){

        when:
        def player = new Player()
        player.setAuthenticatedUserId(blankValues)

        then:
        thrown BlankIdException

        where:
        blankValues<<[""," "]
    }

}
