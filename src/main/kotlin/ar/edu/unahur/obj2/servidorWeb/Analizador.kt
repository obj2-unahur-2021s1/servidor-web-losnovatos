package ar.edu.unahur.obj2.servidorWeb

abstract class Analizador{
    var respuestasDeServidor= mutableListOf<Respuesta>()
    var modulosRecibidos= mutableListOf<Modulo>()

}

class AnalizadorDeDemora(val demoraMinima:Int) :Analizador(){
    fun cantidadDeRespuestasDemoradas()=respuestasDeServidor.count { r->r.tiempo>demoraMinima }
}
class AnalizadorDeIps(val ipsSospechosas: List<String>):Analizador(){
    var pedidosDeIPSSospechosas= mutableListOf<Pedido>()

}