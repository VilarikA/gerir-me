  //demonstrates use of expected exceptions
describe("I18n", function() {
  	var i18n = new I18nEngine();
    var words = {
      "pt_br" : {
        "dog" :"cachorro"
      }
    };
    
    it("Must trasnlate the word if there is", function() {
      var word = "dog";
      expect(i18n.i18n(word, words, 'pt_br')).toEqual("cachorro");
     });

    it("Must return the same word if there isn't", function() {
      expect(i18n.i18n("cat", words, 'pt_br')).toEqual("cat");
     });

    it("Must return the same word if there ins't the lang", function() {
      expect(i18n.i18n("cat", words, 'quenya')).toEqual("cat");
     });
  });