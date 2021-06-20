package ar.edu.unahur.obj2.servidorWeb

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.should
import java.time.LocalDateTime

class ServidorWebTest : DescribeSpec({
  describe("Un servidor web") {
    val servidorWeb1 = ServidorWeb()
    val pedido1 = Pedido("207.46.13.5", "http://pepito.com.ar/documentos/doc1.html", LocalDateTime.now())
    val pedido2 = Pedido("107.40.17.8", "https://pepito.com.ar/documentos/doc3.html", LocalDateTime.now())

    // Requerimiento 1
    describe("realiza un pedido") {
      it("y devuelve 200 ya que el protocolo es el correcto (HTTP).") {
        servidorWeb1.realizarPedido(pedido1).should { Respuesta(CodigoHttp.OK, "", 10, pedido1) }
        servidorWeb1.realizarPedido(pedido1) === Respuesta(CodigoHttp.OK, "", 10, pedido1)
      }
      it("y devuelve 501 ya que el protocolo NO es el correcto (HTTPS).") {
        servidorWeb1.realizarPedido(pedido2).should { Respuesta(CodigoHttp.NOT_IMPLEMENTED, "", 10, pedido2) }
        servidorWeb1.realizarPedido(pedido2) === Respuesta(CodigoHttp.NOT_IMPLEMENTED, "", 10, pedido2)
      }
      // el shouldBe() no funciona para testear.
    }
  }
})
