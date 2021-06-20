package ar.edu.unahur.obj2.servidorWeb

import java.time.LocalDateTime

// Para no tener los códigos "tirados por ahí", usamos un enum que le da el nombre que corresponde a cada código
// La idea de las clases enumeradas es usar directamente sus objetos: CodigoHTTP.OK, CodigoHTTP.NOT_IMPLEMENTED, etc
enum class CodigoHttp(val codigo: Int) {
  OK(200),
  NOT_IMPLEMENTED(501),
  NOT_FOUND(404),
}

class Pedido(val ip: String, val url: String, val fechaHora: LocalDateTime)
class Respuesta(val codigo: CodigoHttp, val body: String, val tiempo: Int, val pedido: Pedido)

class ServidorWeb {
  val protocoloAceptado: String = "http:"
  val modulos = mutableListOf<Modulo>()

  //Requerimientos 1 y 2 implementados.

  fun realizarPedido(pedido: Pedido): Respuesta {
    if(!pedido.url.startsWith(protocoloAceptado)) {
      return Respuesta(CodigoHttp.NOT_IMPLEMENTED, "", 10, pedido)
    }
    val moduloSeleccionado = this.buscarModuloQueSoporteExtension(pedido.url)
    if(moduloSeleccionado == null) {
      return Respuesta(CodigoHttp.NOT_FOUND, "", 10, pedido)
    }
    return Respuesta(CodigoHttp.OK, moduloSeleccionado.body, moduloSeleccionado.tiempoQueTarda, pedido)
  }

  fun buscarModuloQueSoporteExtension(url: String): Modulo? {
    return modulos.find { m -> m.puedeTrabajarCon(url) }
  }
}

class Modulo(val extensiones: Collection<String>, val body: String, val tiempoQueTarda: Int) {
  fun puedeTrabajarCon(url: String) = extensiones.any { ext -> url.endsWith(ext) }
}
