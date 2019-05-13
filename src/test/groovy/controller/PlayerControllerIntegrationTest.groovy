package controller

import hive.player.repository.PlayerRepository
import spock.lang.Specification
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpResponse
import static hive.pandora.constant.HiveInternalHeaders.*

/*Integration test. only work with the system up*/

class PlayerControllerIntegrationTest extends Specification {

    def urlBase = 'http://localhost:9600/'
    def validProfileDataJson = '''
{
    "loginAlias": "custom2",
    "email": "email",
    "telnumber": "11985055502",
    "flavorText": "String grande Lorem Ipsum dolor aquicumsitum amet",
    "birthday": "21/04/1998",
    "cpf": "46358570855",
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

    /*GET*/

    def 'Should return success'() {
        given: 'User exists'
        def client = new RESTClient("${urlBase}", ContentType.JSON)
        when: 'When user info is retrieved'
        client.handler.failure = { resp, data -> resp.setData(data); return resp }
        def response = client.get(headers: ["$AUTHENTICATED_USER_ID": '1'])
        then: 'HttpStatus Ok is received'
        response.status == 200
    }

    def 'Should return not found'() {
        given: 'User does not exists'
        def client = new RESTClient("${urlBase}", ContentType.JSON)
        client.handler.failure = { resp, data -> resp.setData(data); return resp }

        when: 'When user info is retrieved'
        def response = client.get(headers: ["$AUTHENTICATED_USER_ID": '-1'])

        then: 'HttpStatus not found is received'
        response.status == 404
        and: 'Response data null'
        response.data == null
    }

    def 'Should return server bad request'() {
        given: 'User exists'
        def client = new RESTClient("${urlBase}", ContentType.JSON)
        client.handler.failure = { resp, data -> resp.setData(data); return resp }

        when: 'When header is wrong'
        def response = client.get(headers: ['wrong_key': '1'])

        then: 'HttpStatus server bad request is received'
        response.status == 400
    }
    /*POST*/

    def 'Should return success when inserting an user'() {
        given: 'Inserted Json is valid'
        def client = new RESTClient("${urlBase}", ContentType.JSON)
        client.handler.failure = { resp, data -> resp.setData(data); return resp }

        when: 'When user profile data is inserted'
        def response = client.post(
                headers: ["$AUTHENTICATED_USER_ID": '1'],
                body: "$validProfileDataJson")

        then: 'HttpStatus ok request is received'
        response.status == 200
    }

    def 'Should return Bad Request when inserting an empty user'() {
        given: 'Inserted Json is valid'
        def client = new RESTClient("${urlBase}", ContentType.JSON)
        client.handler.failure = { resp, data -> resp.setData(data); return resp }

        when: 'When user profile data is inserted'
        def response = client.post(headers: ["$AUTHENTICATED_USER_ID": '1'])

        then: 'HttpStatus ok request is received'
        response.status == 400
    }

    def 'Should Bad Request when inserting an valid user with wrong header key'() {
        given: 'Inserted Json is valid'
        def client = new RESTClient("${urlBase}", ContentType.JSON)
        client.handler.failure = { resp, data -> resp.setData(data); return resp }

        when: 'When user profile data is inserted'
        def response = client.post(headers: ["wrong_key": '1'])

        then: 'HttpStatus ok request is received'
        response.status == 400
    }
    /*DELETE*/

    def 'Should return Ok status when inserting an empty user'() {
        given:
        def client = new RESTClient("${urlBase}", ContentType.JSON)
        client.handler.failure = { resp, data -> resp.setData(data); return resp }

        when:
        def response = client.delete(headers: ["$AUTHENTICATED_USER_ID": '1'])

        then:
        response.status == 200
    }

    def 'Should Bad Request status when deleting an valid user with wrong header key'() {
        given:
        def client = new RESTClient("${urlBase}", ContentType.JSON)
        client.handler.failure = { resp, data -> resp.setData(data); return resp }

        when:
        def response = client.post(headers: ["wrong_key": '1'])

        then:
        response.status == 400
    }

}
