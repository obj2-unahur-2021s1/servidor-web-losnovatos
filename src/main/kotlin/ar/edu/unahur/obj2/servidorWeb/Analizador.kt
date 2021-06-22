package ar.edu.unahur.obj2.servidorWeb

abstract class Analizador{
    var respuestasDeServidor= mutableListOf<Respuesta>()
    var modulosRecibidos= mutableListOf<Modulo>()

}

class AnalizadorDeDemora(val demoraMinima:Int) :Analizador(){
    fun cantidadDeRespuestasDemoradas(modulo: Modulo)=modulo.respuestas.count { r->r.tiempo>demoraMinima }//creo q es con it
}
class AnalizadorDeIps(val ipsSospechosas: List<String>):Analizador(){
    var pedidosDeIPSSospechosas= mutableListOf<Pedido>()



}