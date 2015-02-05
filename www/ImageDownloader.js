var ImageDownloader = {
    downloadImage: function(successCallback, errorCallback, options) {
    	//var path = null;
        //var assets = null;
        //var quote = null;
        //var cutin = null
        
        /*if (options) {
        	path = options.path;
        	assets = options.assets;
        	quote = options.quote;
        	cutin = options.cutin;
        }*/
        
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'ImageDownloader', // mapped to our native Java class called "RecordVideo"
            'downloadImage', // with this action name
            [options]
        ); 
    }
}
module.exports = ImageDownloader;