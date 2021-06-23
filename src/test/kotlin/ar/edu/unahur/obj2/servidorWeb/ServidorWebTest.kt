package ar.edu.unahur.obj2.servidorWeb

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ServidorWebTest : DescribeSpec({
  describe("Un servidor web") {
    val servidorWeb1 = ServidorWeb()
    val pedido1 = Pedido("207.46.13.5", "http://pepito.com.ar/documentos/doc1.html", LocalDateTime.now())
    val pedido2 = Pedido("107.40.17.8", "https://pepito.com.ar/documentos/doc3.html", LocalDateTime.now())
    val pedido3 = Pedido("147.50.27.1", "http://pepito.com.ar/documentos/foto1.jpg", LocalDateTime.now())
    val pedido4 = Pedido("192.168.111.10", "http://google.com.ar.png",LocalDateTime.now())
    val pedido5 = Pedido("192.168.110.13", "http://youtube.com.ar.txt",
      LocalDateTime.now())

    val modulo1 = Modulo(setOf<String>("html"), "modulo web", 5)
    val modulo2 = Modulo(mutableListOf("png", "jpg", "gif"), "img", 15 )
    val modulo3 = Modulo(mutableListOf("txt", "doc", "docx"), "doc", 50 )
    val analizadorIpSospechosa = AnalizadorDeIps(mutableListOf("107.40.17.8", "207.46.13.5","147.50.27.1"))
    val analizadorDemora=AnalizadorDeDemora(15)
    servidorWeb1.modulos.add(modulo1)

    // Requerimiento 1
    describe("realiza un pedido") {
      it("y devuelve 200 ya que el protocolo es el correcto (HTTP).") {
        val respuesta = servidorWeb1.realizarPedido(pedido1)
        respuesta.codigo.shouldBe(CodigoHttp.OK)
        respuesta.body.shouldBe("modulo web")
        respuesta.pedido.shouldBe(pedido1)
        respuesta.tiempo.shouldBe(5)
      }
      it("y devuelve 501 ya que el protocolo NO es el correcto (HTTPS).") {
        val respuesta = servidorWeb1.realizarPedido(pedido2)
        respuesta.codigo.shouldBe(CodigoHttp.NOT_IMPLEMENTED)
        respuesta.body.shouldBe("")
        respuesta.pedido.shouldBe(pedido2)
        respuesta.tiempo.shouldBe(10)
      }
      // Requerimiento 2 ya implementado y se agrega el 404.
      it("y devuelve 404 ya que no hay ningun modulo que pueda atender el pedido.") {
        val respuesta = servidorWeb1.realizarPedido(pedido3)
        respuesta.codigo.shouldBe(CodigoHttp.NOT_FOUND)
        respuesta.body.shouldBe("")
        respuesta.pedido.shouldBe(pedido3)
        respuesta.tiempo.shouldBe(10)
      }
      describe( "Analizadores") {
        servidorWeb1.modulos.add(modulo2)
        servidorWeb1.modulos.add(modulo3)
        servidorWeb1.analizadores.add(analizadorDemora)
        servidorWeb1.analizadores.add(analizadorIpSospechosa)

        servidorWeb1.realizarPedido(pedido4)
        servidorWeb1.realizarPedido(pedido5)

        describe("Respuestas Demoradas") {
          it("Modulo2") {
            analizadorDemora.cantidadDeRespuestasDemoradas(modulo2).shouldBe(0)}
        }
        describe("Analizador de ips") {
          describe("pedidos de ip sospechosa.") {
            it ("192.168.110.11") {
              analizadorIpSospechosa.cantPedidosDeIpSospechosa("192.168.111.10").shouldBe(1)
            }
    }
  }
}}}})
