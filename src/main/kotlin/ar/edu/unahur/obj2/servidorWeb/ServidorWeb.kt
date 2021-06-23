package ar.edu.unahur.obj2.servidorWeb

import java.time.LocalDateTime

// Para no tener los códigos "tirados por ahí", usamos un enum que le da el nombre que corresponde a cada código
// La idea de las clases enumeradas es usar directamente sus objetos: CodigoHTTP.OK, CodigoHTTP.NOT_IMPLEMENTED, etc
enum class CodigoHttp(val codigo: Int) {
  OK(200),
  NOT_IMPLEMENTED(501),
  NOT_FOUND(404),
}

class Pedido(val ip: String, val url: String, val fechaHora: LocalDateTime){
  fun protocolo() = url.split(":/").get(0)
  fun ruta() = url.split(":/").get(1)
  fun extension() = url.split(".").last()
}
class Respuesta(val codigo: CodigoHttp, val body: String, val tiempo: Int, val pedido: Pedido,val modulo: Modulo)

class ServidorWeb {

  val modulos = mutableListOf<Modulo>()
  val analizadores= mutableListOf<Analizador>()
  var pedidosRealizados= mutableListOf<Pedido>()
  fun hayModuloQueResponda(pedido: Pedido) = modulos.any { it.puedeResponderA(pedido)}
  fun realizarPedido(pedido: Pedido)=
    if (hayModuloQueResponda(pedido) && pedido.protocolo().equals("http")) {
      analizadores.forEach{it.respuestasDeServidor.add(moduloQuePuedeResponder(pedido).generarRespuestaA(pedido))}
      analizadores.forEach{it.modulosRecibidos.add(moduloQuePuedeResponder(pedido))}
      moduloQuePuedeResponder(pedido).generarRespuestaA(pedido)
    } else {
      analizadores.forEach{it.respuestasDeServidor.add(moduloQuePuedeResponder(pedido).generarRespuestaA(pedido))}
      analizadores.forEach{it.modulosRecibidos.add(moduloQuePuedeResponder(pedido))}
      noModulo.generarRespuestaA(pedido)
    }



  fun moduloQuePuedeResponder(pedido: Pedido) = modulos.first { it.puedeResponderA(pedido) }
}

open class Modulo(val extensiones: Collection<String>, val body: String, val tiempoQueTarda: Int) {
  val respuestas= mutableListOf<Respuesta>()
  val pedidos= mutableListOf<Pedido>()
  open fun generarRespuestaA(pedido: Pedido): Respuesta {
    val respuesta: Respuesta
    respuesta = Respuesta(CodigoHttp.OK, body, tiempoQueTarda, pedido, this)
    respuestas.add(respuesta)
    pedidos.add(pedido)
    return respuesta
  }
  fun puedeTrabajarCon(url: String) = extensiones.any { ext -> url.endsWith(ext) }
  fun puedeResponderA (pedido: Pedido) = extensiones.any { it.equals(pedido.extension())}
}
object noModulo: Modulo(mutableListOf<String>(), "",10) {
  override fun generarRespuestaA(pedido: Pedido): Respuesta {
    val respuesta =
      if (pedido.protocolo().equals("http")) {
        Respuesta(CodigoHttp.NOT_FOUND, body, tiempoQueTarda, pedido, this)
      } else {
        Respuesta(CodigoHttp.NOT_IMPLEMENTED, body, tiempoQueTarda, pedido, this)
      }

    respuestas.add(respuesta)
    pedidos.add(pedido)
    return respuesta
  }
}

