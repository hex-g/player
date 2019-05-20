package hive.player.controller

import hive.player.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Shared
import spock.lang.Specification
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

import static hive.pandora.constant.HiveInternalHeaders.*

/*Integration test. only work with the system up*/
class PlayerControllerIntegrationTest extends Specification {

    @Shared
    def integrationTestHeaderValue="INTEGRATION"
    @Shared
    RESTClient client

    def setup(){
        client = new RESTClient("${urlBase}", ContentType.JSON)
        client.handler.failure = { resp, data -> resp.setData(data); return resp }
    }

    def urlBase = 'http://localhost:9600/'

    def validProfileDataJson = '''
{
    "loginAlias": "Integration_Test",
    "email": "integration@test.com",
    "telnumber": "11987654321",
    "flavorText": "Large string Lorem Ipsum dolor aquicumsitum amet",
    "birthday": "21/04/1998",
    "options": {
        "laurel_wreath": "CHALLENGER",
        "honorific": "Coffee Titan",
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

    def cleanupSpec(){
        //should have some delete method in the original application
    }

    def 'Should GET user successfully'() {

        given: 'perform GET with correct header'
        def response = client.get(
                headers: ["$AUTHENTICATED_USER_ID": "${integrationTestHeaderValue}"])

        expect: 'HttpStatus OK'
        response.status == 200
    }

    def 'Should POST an user successfully'() {

        given: 'perform POST with valid Json'
        def response = client.post(
                headers: ["$AUTHENTICATED_USER_ID": "${integrationTestHeaderValue}"],
                body: "$validProfileDataJson")

        expect: 'HttpStatus OK'
        response.status == 200
    }

    def 'Should return BAD REQUEST when GET with wrong Header key'() {

        given: 'perform GET with wrong Header key'
        def response = client.get(
                headers: ['wrong_key': "${integrationTestHeaderValue}"])

        expect: 'HttpStatus BAD REQUEST'
        response.status == 400
    }

    def 'Should return BAD REQUEST when POST with wrong Header key'() {

        given: 'perform POST with wrong Header key'
        def response = client.post(
                headers: ["wrong_key": '1'])

        expect: 'HttpStatus BAD REQUEST'
        response.status == 400
    }

    def 'Should return BAD REQUEST when POST with an empty body request'() {

        given: 'perform POST with empty body request'
        def response = client.post(
                headers: ["$AUTHENTICATED_USER_ID": "${integrationTestHeaderValue}"])

        expect: 'HttpStatus BAD REQUEST'
        response.status == 400
    }

    def 'Should return BAD REQUEST when POST a body request with PlayerId'() {

        given: 'Json containing playerId as Header key'
        def invalidProfileDataJson='{"playerId": 10}'

        when: 'When user profile data is inserted'
        def response = client.post(
                                    headers: ["$AUTHENTICATED_USER_ID": "${integrationTestHeaderValue}"],
                                    body: ["$invalidProfileDataJson"])


        then: 'HttpStatus ok request is received'
        response.status == 400
    }

}
