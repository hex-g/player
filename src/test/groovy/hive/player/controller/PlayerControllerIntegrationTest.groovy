package hive.player.controller

import hive.player.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Shared
import spock.lang.Specification
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

import static hive.pandora.constant.HiveInternalHeaders.*

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

    def validProfileDataJson = $/
        {
            "loginAlias": "Integration_Test",
            "email": "integration@test.com",
            "telnumber": "11987654321",
            "flavorText": "Large string Lorem Ipsum dolor aquicumsitum amet",
            "birthday": "21/04/1998",
            "options": {
                "laurel_wreath": "CHALLENGER",
                "honorific": "Coffee Titan",
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
    /$

    def cleanupSpec(){
        //should have some delete method in the original application
    }

    def 'Perform GET with correct header and expect HttpStatus OK'() {

        given:
        def response = client.get(
                headers: ["$AUTHENTICATED_USER_ID": "${integrationTestHeaderValue}"])

        expect:
        response.status == 200
    }

    def 'Perform POST with valid Json and expect HttpStatus OK'() {

        given:
        def response = client.post(
                headers: ["$AUTHENTICATED_USER_ID": "${integrationTestHeaderValue}"],
                body: "$validProfileDataJson")

        expect:
        response.status == 200
    }

    def 'Perform GET with wrong Header key and expect HttpStatus BAD REQUEST'() {

        given:
        def response = client.get(
                headers: ['wrong_key': "${integrationTestHeaderValue}"])

        expect:
        response.status == 400
    }

    def 'Perform POST with wrong Header key and expect HttpStatus BAD REQUEST'() {

        given:
        def response = client.post(
                headers: ["wrong_key": '1'])

        expect:
        response.status == 400
    }

    def 'Perform POST with empty body request and expect HttpStatus BAD REQUEST'() {

        given:
        def response = client.post(
                headers: ["$AUTHENTICATED_USER_ID": "${integrationTestHeaderValue}"])

        expect:
        response.status == 400
    }

    def '''
        Given Json containing playerId,
        when user profile data is inserted,
        then HttpStatus BAD REQUEST
        '''() {

        given:
        def invalidProfileDataJson='{"playerId": 10}'

        when:
        def response = client.post(
                                    headers: ["$AUTHENTICATED_USER_ID": "${integrationTestHeaderValue}"],
                                    body: ["$invalidProfileDataJson"])

        then:
        response.status == 400
    }

}
