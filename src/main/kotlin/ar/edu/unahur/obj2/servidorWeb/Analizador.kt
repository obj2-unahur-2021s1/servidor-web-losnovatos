package ar.edu.unahur.obj2.servidorWeb

import java.time.LocalDateTime

abstract class Analizador{
    var respuestasDeServidor= mutableSetOf<Respuesta>()
    var modulosRecibidos= mutableSetOf<Modulo>()

}

class AnalizadorDeDemora(val demoraMinima:Int) :Analizador(){
    fun cantidadDeRespuestasDemoradas(modulo: Modulo)=modulo.respuestas.count { it.tiempo>demoraMinima }//creo q es con it
}
class AnalizadorDeIps(val ipsSospechosas: List<String>):Analizador(){
    var pedidosDeIPSSospechosas= mutableListOf<Pedido>()
    fun cantPedidosDeIpSospechosa(ip:String)=respuestasDeServidor.count{it.pedido.ip==ip}
    fun modulos()=respuestasDeServidor.map{it->it.modulo}//no me acuerdo si repite, probar.
    fun cantConsultasSospechosasA(modulo: Modulo) = modulo.pedidos.count { ipsSospechosas.contains(it.ip) }
    fun moduloMasConsultado()=modulos().maxBy{cantConsultasSospechosasA(it)}
    fun ipsQueRequirieron(ruta:String)=pedidosDeIPSSospechosas.filter{it.ruta()==ruta}.map{} //probar.
}
class AnalizadorEstadistico():Analizador(){
    fun tiempoDeRespuestaPromedio()=respuestasDeServidor.sumBy { it.tiempo }/respuestasDeServidor.size
    fun cantPedidosEntre(fecha1:LocalDateTime,fecha2:LocalDateTime)=respuestasDeServidor.count{it.pedido.fechaHora in fecha1..fecha2}
    fun cantRespuestasCon(palabra:String)=respuestasDeServidor.count { it.body.contains(palabra) }
    fun cantPedidosConRespuestaExitosa()=respuestasDeServidor.count{it.codigo==CodigoHttp.OK}

}