 //demonstrates use of expected exceptions
describe("Cadastrando Eventos", function() {
    it("Start e o primeiro paramentro passado", function() {
        var eventBussy = new EventBussy(1);
        expect(eventBussy.start).toEqual(1);
     });

    it("Must return the same word if there isn't", function() {
      expect(i18n.i18n("cat", words, 'pt_br')).toEqual("cat");
     });

    it("Must return the same word if there ins't the lang", function() {
      expect(i18n.i18n("cat", words, 'quenya')).toEqual("cat");
     });
  });