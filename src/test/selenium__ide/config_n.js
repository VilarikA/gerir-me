exports.config = {
  seleniumAddress: 'http://localhost:4444/wd/hub',
 // seleniumAddress: 'http://localhost:4444/wd/hub',
  //specs: []
  //specs: ['crud.spec.js','navegation.spec.js', 'demo.spec.js']
  specs: ['migrar_margaeth_update_n.js'],
  defaultTimeoutInterval : 10000000000
};