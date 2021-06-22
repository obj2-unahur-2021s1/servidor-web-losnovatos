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
    fun cantPedidosDeIpSospechosa(ip:String)=respuestasDeServidor.count{it.pedido.ip==ip}
    fun modulos()=respuestasDeServidor.map{it->it.modulo}//no me acuerdo si repite, probar.
    fun cantConsultasSospechosasA(modulo: Modulo) = modulo.pedidos.count { ipsSospechosas.contains(it.ip) }
    fun moduloMasConsultado()=modulos().maxBy{cantConsultasSospechosasA(it)}
    fun ipsQueRequirieron(ruta:String)=pedidosDeIPSSospechosas.filter{it.ruta()==ruta}.map{} //probar.




}