var ImageDownloader = {
    downloadImage: function(successCallback, errorCallback, options) {

        //var assets = null;
        
        if (options) {
        	assets = options.assets;
        }
        
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