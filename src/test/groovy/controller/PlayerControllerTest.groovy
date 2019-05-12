package controller

import spock.lang.Specification
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.apache.http.HttpResponse
import static hive.pandora.constant.HiveInternalHeaders.*

class PlayerControllerTest extends Specification {
    def urlBase = 'http://localhost:9600/'
    def 'Should return success'() {
        given:'User exists'
        def cliente = new RESTClient("${urlBase}", ContentType.JSON)
        when:'When user info is retrieved'
        def resposta = cliente.get(headers: ["$AUTHENTICATED_USER_ID": '1'])
        then:'HttpStatus Ok is received'
        resposta.status == 200
        and:
        println()
        println resposta.data
    }
}
