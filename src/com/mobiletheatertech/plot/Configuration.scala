package com.mobiletheatertech.plot

//import com.mobiletheatertech.plot.ArgumentException
import java.io.File
import java.net.URI
import java.nio.file.WatchEvent.{Modifier, Kind}
import java.nio.file._
import java.util

/**
 * Created by dhs on 7/19/15.
 */
object Configuration {

  val home: String = System.getProperty("user.home")

  val SourceDefault = "/Dropbox/Plot/plotfiles/"
  val SinkDefault = "/Dropbox/Plot/out/"

  var SourceDirectory = ""
  var SinkDirectory = ""
  var BaseName = ""

  def Initialize( args : Array[ String ] ): Unit = {
    if (1 > args.length) {
      throw new ArgumentException ("Not enough arguments");
    }
    if (2 < args.length) {
      throw new ArgumentException("Too many arguments")
    }

    var foo = new File( args(0) )
    BaseName = foo.getName()

    val home: String = System.getProperty("user.home")

    SourceDirectory = home + "/Dropbox/Plot/plotfiles/"
    SinkDirectory = home + "/Dropbox/Plot/out/" + BaseName + "/"

    SourceDirectory = figurePath( SourceDefault, args(0) )

    if (2 == args.length) {
      var sinkPath = args(1)
      if (! sinkPath.charAt(sinkPath.length() - 1).equals('/')) {
        sinkPath += "/"
      }
      SinkDirectory = figurePath( SinkDefault, sinkPath ) + BaseName + "/"
    }
  }

  def figurePath( default : String, arg : String ) : String = {
    var foo = new File( arg )
    var pathname = ""

    try {
      pathname =
        if (arg.charAt(arg.length() - 1).equals('/')) {
          arg
        }
        else {
          foo.getParent()
        }
    }
    catch {
      case e: Exception => throw new ArgumentException( "Missing required plotfile name")
    }
//    var basename = foo.getName()

    var result = if (! arg.contains( '/' ) ) {
     home + default
    }
    else if( arg.startsWith("~/")) {
      home + pathname.substring(1)
    }
    else if( arg.charAt(0).equals('.') || arg.charAt(0).equals('/')) {
      pathname
    }
    else {
      home + '/' + pathname
    }

    if( '/' != result.charAt( result.length() - 1 )) result += "/"

    result
  }
}
