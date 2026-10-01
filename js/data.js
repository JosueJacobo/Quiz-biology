// Question Bank and Level System for BioQuiz: Reino Vivo

const DIFFICULTY_TIERS = {
  tier1: {
    id: 'tier1',
    name: 'Fácil',
    icon: '🌱',
    color: '#4CAF50',
    range: [1, 20],
    title: 'Botánica Fundamental',
    pointsPerQuestion: 100,
    timeLimit: 20
  },
  tier2: {
    id: 'tier2',
    name: 'Intermedio',
    icon: '🌿',
    color: '#8BC34A',
    range: [21, 40],
    title: 'Zoología de Invertebrados',
    pointsPerQuestion: 150,
    timeLimit: 18
  },
  tier3: {
    id: 'tier3',
    name: 'Avanzado',
    icon: '🌲',
    color: '#FFB300',
    range: [41, 60],
    title: 'Vertebrados y Adaptación',
    pointsPerQuestion: 200,
    timeLimit: 15
  },
  tier4: {
    id: 'tier4',
    name: 'Experto',
    icon: '🔬',
    color: '#FB8C00',
    range: [61, 80],
    title: 'Fisiología Celular y Genética',
    pointsPerQuestion: 250,
    timeLimit: 15
  },
  tier5: {
    id: 'tier5',
    name: 'Maestro',
    icon: '👑',
    color: '#E53935',
    range: [81, 100],
    title: 'Ecología Global y Evolución',
    pointsPerQuestion: 300,
    timeLimit: 12
  }
};

function getTierForLevel(levelNum) {
  if (levelNum <= 20) return DIFFICULTY_TIERS.tier1;
  if (levelNum <= 40) return DIFFICULTY_TIERS.tier2;
  if (levelNum <= 60) return DIFFICULTY_TIERS.tier3;
  if (levelNum <= 80) return DIFFICULTY_TIERS.tier4;
  return DIFFICULTY_TIERS.tier5;
}

// 100 Complete Defined Levels
const LEVELS_METADATA = [
  // 1-20: BOTÁNICA FUNDAMENTAL (FÁCIL)
  { id: 1, title: "Anatomía de la Flor", category: "Botánica", icon: "🌸", desc: "Pétalos, sépalos, estambres y pistilos" },
  { id: 2, title: "Fotosíntesis Básica", category: "Botánica", icon: "☀️", desc: "Luz solar, dióxido de carbono y glucosa" },
  { id: 3, title: "El Secreto de la Clorofila", category: "Botánica", icon: "🍃", desc: "Pigmentos verdes y captación de fotones" },
  { id: 4, title: "Gimnospermas y Coníferas", category: "Botánica", icon: "🌲", desc: "Pinos, abetos y semillas desnudas" },
  { id: 5, title: "Angiospermas: Frutos y Semillas", category: "Botánica", icon: "🍎", desc: "Plantas con flores y dispersión frutal" },
  { id: 6, title: "Briófitas y Musgos", category: "Botánica", icon: "🌱", desc: "Plantas no vasculares de ambientes húmedos" },
  { id: 7, title: "Vasos de Conducción: Xilema", category: "Botánica", icon: "💧", desc: "Transporte ascendente de agua y sales" },
  { id: 8, title: "Nutrición Vegetal: Floema", category: "Botánica", icon: "🍯", desc: "Distribución de savia elaborada y azúcares" },
  { id: 9, title: "Respiración: Estomas", category: "Botánica", icon: "🌬️", desc: "Intercambio gaseoso y transpiración foliar" },
  { id: 10, title: "Morfología de la Raíz", category: "Botánica", icon: "🥕", desc: "Anclaje, cofia y pelos absorbentes" },
  { id: 11, title: "El Proceso de Germinación", category: "Botánica", icon: "🌾", desc: "Despertar del embrión y cotiledones" },
  { id: 12, title: "Polinización y Vectores", category: "Botánica", icon: "🐝", desc: "Abejas, viento, mariposas y colibríes" },
  { id: 13, title: "Simbiosis: Micorrizas", category: "Botánica", icon: "🍄", desc: "Alianza subterránea entre hongos y raíces" },
  { id: 14, title: "Helechos y Esporas", category: "Botánica", icon: "🌿", desc: "Pteridófitas y alternancia de generaciones" },
  { id: 15, title: "Hojas y Fototropismo", category: "Botánica", icon: "🪴", desc: "Movimiento orientado hacia la luz solar" },
  { id: 16, title: "Plantas Carnívoras", category: "Botánica", icon: "🪴", desc: "Trampas foliares y suelos pobres en nitrógeno" },
  { id: 17, title: "Líquenes Centinelas", category: "Botánica", icon: "🪨", desc: "Bioindicadores simbióticos de aire puro" },
  { id: 18, title: "Algas Verdes y Origen Vegetal", category: "Botánica", icon: "🌊", desc: "Clorofitas ancestrales y conquista terrestre" },
  { id: 19, title: "Cactáceas y Xerófitas", category: "Botánica", icon: "🌵", desc: "Adaptaciones a la sequía y metabolismo CAM" },
  { id: 20, title: "Hormonas Vegetales: Auxinas", category: "Botánica", icon: "🧬", desc: "Regulación del crecimiento y dominancia apical" },

  // 21-40: ZOOLOGÍA DE INVERTEBRADOS (INTERMEDIO)
  { id: 21, title: "Reino Animal e Invertebrados", category: "Zoología", icon: "🐛", desc: "La inmensa mayoría de la fauna terrestre" },
  { id: 22, title: "Artrópodos Dominantes", category: "Zoología", icon: "🦗", desc: "Patas articuladas y exoesqueleto de quitina" },
  { id: 23, title: "Moluscos y Conchas", category: "Zoología", icon: "🐚", desc: "Cuerpo blando, manto y concha calcárea" },
  { id: 24, title: "Anélidos: Lombrices de Tierra", category: "Zoología", icon: "🪱", desc: "Segmentación metamérica y fertilidad del suelo" },
  { id: 25, title: "Cnidarios: Medusas y Corales", category: "Zoología", icon: "🪼", desc: "Cnidocitos urticantes y simetría radial" },
  { id: 26, title: "Equinodermos: Estrellas de Mar", category: "Zoología", icon: "⭐", desc: "Sistema hidrovascular ambulacral y espinas" },
  { id: 27, title: "Gusanos Planos: Platelmintos", category: "Zoología", icon: "🧬", desc: "Planarias regenerativas y tenias parásitas" },
  { id: 28, title: "Poríferos: Esponjas Silenciosas", category: "Zoología", icon: "🧽", desc: "Coanocitos filtradores sin tejidos verdaderos" },
  { id: 29, title: "El Mundo de los Insectos", category: "Zoología", icon: "🐞", desc: "Cabeza, tórax, abdomen y 6 patas marchadoras" },
  { id: 30, title: "Arácnidos: Arañas y Escorpiones", category: "Zoología", icon: "🕷️", desc: "Quelíceros venenosos, pedipalpos y 8 patas" },
  { id: 31, title: "Crustáceos Acuáticos", category: "Zoología", icon: "🦀", desc: "Cangrejos, langostas y caparazones calcificados" },
  { id: 32, title: "Cefalópodos Inteligentes", category: "Zoología", icon: "🐙", desc: "Pulpos, calamares, ojos complejos y cromatóforos" },
  { id: 33, title: "Gasterópodos: Caracoles y Babosas", category: "Zoología", icon: "🐌", desc: "Pie muscular, rádula raspadora y torsión" },
  { id: 34, title: "Bivalvos Filtradores", category: "Zoología", icon: "🦪", desc: "Almejas, mejillones y perlas del océano" },
  { id: 35, title: "Miriápodos: Ciempiés y Milpiés", category: "Zoología", icon: "🐛", desc: "Depredadores venenosos y descomponedores" },
  { id: 36, title: "Metamorfosis Holometábola", category: "Zoología", icon: "🦋", desc: "Huevo, oruga, crisálida y mariposa adulta" },
  { id: 37, title: "Exoesqueleto y Muda (Ecdisis)", category: "Zoología", icon: "🛡️", desc: "Desprenderse de la cutícula para crecer" },
  { id: 38, title: "Ojos Compuestos y Ocelos", category: "Zoología", icon: "👀", desc: "Ommatidios y detección ultrasónica de movimientos" },
  { id: 39, title: "Sociedades de Insectos", category: "Zoología", icon: "🐜", desc: "Hormigas, abejas melíferas y castas obreras" },
  { id: 40, title: "Bioquímica del Veneno", category: "Zoología", icon: "🧪", desc: "Neurotoxinas, hemotoxinas y presas inmovilizadas" },

  // 41-60: VERTEBRADOS Y ADAPTACIÓN (AVANZADO)
  { id: 41, title: "El Filo Cordados", category: "Zoología", icon: "🐟", desc: "Notocorda, hendiduras faríngeas y tubo neural" },
  { id: 42, title: "Peces Óseos (Osteictios)", category: "Zoología", icon: "🐠", desc: "Vejiga natatoria, opérculo y escamas cicloideas" },
  { id: 43, title: "Tiburones y Rayas (Condrictios)", category: "Zoología", icon: "🦈", desc: "Esqueleto cartilaginoso y ampollas de Lorenzini" },
  { id: 44, title: "Anfibios: Doble Vida", category: "Zoología", icon: "🐸", desc: "Respiración cutánea, metamorfosis y renacuajos" },
  { id: 45, title: "Reptiles y el Huevo Amniota", category: "Zoología", icon: "🦎", desc: "Emancipación reproductiva del agua" },
  { id: 46, title: "Serpientes y Órgano de Jacobson", category: "Zoología", icon: "🐍", desc: "Quimiorrecepción lingual y fosetas térmicas" },
  { id: 47, title: "Cocodrilos: Fósiles Vivientes", category: "Zoología", icon: "🐊", desc: "Poderosa mordida y corazón de 4 cámaras" },
  { id: 48, title: "Aves y la Mecánica del Vuelo", category: "Zoología", icon: "🦅", desc: "Huesos neumáticos, quilla esternal y sacos aéreos" },
  { id: 49, title: "Plumas: Estructura y Aislamiento", category: "Zoología", icon: "🪶", desc: "Barbas, barbillas y conservación de calor" },
  { id: 50, title: "Aves Rapaces y Visión Aguda", category: "Zoología", icon: "🦉", desc: "Fóveas dobles y garras prensiles letales" },
  { id: 51, title: "Mamíferos Monotremas", category: "Zoología", icon: "🦆", desc: "Ornitorrincos y equidnas: mamíferos ovíparos" },
  { id: 52, title: "Marsupiales y Desarrollo Externo", category: "Zoología", icon: "🦘", desc: "Canguros, koalas y la bolsa marsupial" },
  { id: 53, title: "Mamíferos Placentarios", category: "Zoología", icon: "🐺", desc: "Nutrición intrauterina prolongada y neocórtex" },
  { id: 54, title: "Cetáceos y Retorno al Océano", category: "Zoología", icon: "🐋", desc: "Ballenas, delfines y respiración por espiráculo" },
  { id: 55, title: "Murciélagos y Ecolocalización", category: "Zoología", icon: "🦇", desc: "Vuelo activo en mamíferos y ultrasonidos" },
  { id: 56, title: "Termorregulación Animal", category: "Zoología", icon: "🌡️", desc: "Ectotermia frente a endotermia constante" },
  { id: 57, title: "Camuflaje y Cripsis", category: "Zoología", icon: "🦎", desc: "Mimetismo batesiano, mulleriano y coloración críptica" },
  { id: 58, title: "Migraciones Épicas", category: "Zoología", icon: "🧭", desc: "Orientación magnética en aves y tortugas marinas" },
  { id: 59, title: "Hibernación y Letargo", category: "Zoología", icon: "🐻", desc: "Reducción metabólica y supervivencia invernal" },
  { id: 60, title: "Primates y Encefalización", category: "Zoología", icon: "🐒", desc: "Visión estereoscópica y pulgar oponible" },

  // 61-80: FISIOLOGÍA CELULAR Y GENÉTICA (EXPERTO)
  { id: 61, title: "Teoría Celular Moderna", category: "Genética", icon: "🔬", desc: "La unidad básica de toda vida conocida" },
  { id: 62, title: "Estructura de la Membrana Plasmática", category: "Fisiología", icon: "🫧", desc: "Mosaico fluido de bicapa lipídica y proteínas" },
  { id: 63, title: "Transporte Pasivo y Osmosis", category: "Fisiología", icon: "⚖️", desc: "Gradientes electroquímicos y acuaporinas" },
  { id: 64, title: "Transporte Activo y Bomba Na+/K+", category: "Fisiología", icon: "⚡", desc: "Gasto de ATP para mantener potenciales iónicos" },
  { id: 65, title: "Mitocondria y Respiración Celular", category: "Fisiología", icon: "🔋", desc: "Cadena de transporte de electrones y fosforilación" },
  { id: 66, title: "El Ciclo de Krebs", category: "Fisiología", icon: "🔄", desc: "Oxidación de acetil-CoA y producción de NADH" },
  { id: 67, title: "Cloroplastos y Fase Luminosa", category: "Botánica", icon: "🍃", desc: "Fotólisis del agua en tilacoides y síntesis de NADPH" },
  { id: 68, title: "Ciclo de Calvin-Benson", category: "Botánica", icon: "🌀", desc: "Fijación del carbono con la enzima Rubisco" },
  { id: 69, title: "La Doble Hélice del ADN", category: "Genética", icon: "🧬", desc: "Watson, Crick, Franklin y bases nitrogenadas" },
  { id: 70, title: "Replicación Semiconservativa", category: "Genética", icon: "🧪", desc: "Helicasa, ADN polimerasa y fragmentos de Okazaki" },
  { id: 71, title: "Transcripción de ARN Mensajero", category: "Genética", icon: "📜", desc: "De gen nuclear a transcrito maduro procesado" },
  { id: 72, title: "El Código Genético Universal", category: "Genética", icon: "📖", desc: "Codones tripletes de nucleótidos y aminoácidos" },
  { id: 73, title: "Traducción en Ribosomas", category: "Genética", icon: "🧵", desc: "ARN de transferencia y ensamblaje de polipéptidos" },
  { id: 74, title: "Mitosis y División Nuclear", category: "Genética", icon: "✂️", desc: "Profase, metafase, anafase y telofase celular" },
  { id: 75, title: "Meiosis y Entrecruzamiento", category: "Genética", icon: "🔀", desc: "Crossover en profase I y variabilidad gamética" },
  { id: 76, title: "Leyes de Gregor Mendel", category: "Genética", icon: "🌱", desc: "Segregación independiente y cuadros de Punnett" },
  { id: 77, title: "Herencia Ligada al Sexo", category: "Genética", icon: "⚧️", desc: "Cromosomas X e Y, daltonismo y hemofilia" },
  { id: 78, title: "Mutaciones Genéticas y Cromosómicas", category: "Genética", icon: "💥", desc: "Sustituciones, deleciones y translocaciones" },
  { id: 79, title: "Epigenética y Metilación", category: "Genética", icon: "🏷️", desc: "Modificación de la expresión sin cambiar la secuencia" },
  { id: 80, title: "Ingeniería Genética y CRISPR-Cas9", category: "Genética", icon: "✂️", desc: "Tijeras moleculares y edición precisa del genoma" },

  // 81-100: ECOLOGÍA GLOBAL Y EVOLUCIÓN (MAESTRO)
  { id: 81, title: "Selección Natural Darwiniana", category: "Evolución", icon: "⛵", desc: "Descendencia con modificación y éxito reproductivo" },
  { id: 82, title: "Especiación Alopátrica y Simpátrica", category: "Evolución", icon: "⛰️", desc: "Aislamiento geográfico y reproductivo de linajes" },
  { id: 83, title: "Deriva Génica y Efecto Fundador", category: "Evolución", icon: "🎲", desc: "Azar en poblaciones pequeñas y cuellos de botella" },
  { id: 84, title: "Radiación Adaptativa de Pinzones", category: "Evolución", icon: "🐦", desc: "Diversificación rápida hacia nichos ecológicos vacíos" },
  { id: 85, title: "Homologías y Órganos Vestigiales", category: "Evolución", icon: "🦴", desc: "Extremidades compartidas y vestigios del pasado" },
  { id: 86, title: "Evolución Convergente", category: "Evolución", icon: "🦈", desc: "Formas hidrodinámicas similares en linajes lejanos" },
  { id: 87, title: "Fósiles Guía y Estratigrafía", category: "Evolución", icon: "🪨", desc: "Datación radiométrica y registro paleontológico" },
  { id: 88, title: "La Explosión Cámbrica", category: "Evolución", icon: "💥", desc: "Aparición súbita de la mayoría de planes corporales" },
  { id: 89, title: "Las Grandes Extinciones Masivas", category: "Evolución", icon: "☄️", desc: "Pérmico, Cretácico-Paleógeno y la 6ª extinción" },
  { id: 90, title: "Coevolución: Flores y Polinizadores", category: "Ecología", icon: "🌺", desc: "Carreras armamentísticas y mutualismos simbióticos" },
  { id: 91, title: "Cadenas y Redes Tróficas", category: "Ecología", icon: "🕸️", desc: "Productores, consumidores y flujo de biomasa" },
  { id: 92, title: "Ciclo Biogeoquímico del Nitrógeno", category: "Ecología", icon: "🧪", desc: "Fijación por Rhizobium, nitrificación y desnitrificación" },
  { id: 93, title: "Ciclo del Carbono y Clima Global", category: "Ecología", icon: "🌍", desc: "Sumideros oceánicos, bosques y efecto invernadero" },
  { id: 94, title: "Especies Clave y Cascadas Tróficas", category: "Ecología", icon: "🦦", desc: "Nutrias, estrellas de mar y lobos en Yellowstone" },
  { id: 95, title: "Sucesión Ecológica Primaria", category: "Ecología", icon: "🌋", desc: "Colonización de lava desnuda hasta el bosque clímax" },
  { id: 96, title: "Biomas: De la Selva a la Tundra", category: "Ecología", icon: "❄️", desc: "Gradientes de temperatura, humedad y biodiversidad" },
  { id: 97, title: "Arrecifes de Coral en Peligro", category: "Ecología", icon: "🪸", desc: "Blanqueamiento coralino y acidificación oceánica" },
  { id: 98, title: "Biogeografía de Islas", category: "Ecología", icon: "🏝️", desc: "Teoría de MacArthur-Wilson: área y distancia continental" },
  { id: 99, title: "Capacidad de Carga y Dinámica", category: "Ecología", icon: "📈", desc: "Curvas logísticas sigmoideas y factores denso-dependientes" },
  { id: 100, title: "La Cima de la Biosfera", category: "Ecología", icon: "🌐", desc: "El gran desafío de la biodiversidad en el Antropoceno" }
];

// Rich curated question database
const CURATED_QUESTIONS = [
  // Nivel 1: Anatomía de la Flor
  {
    levelId: 1,
    question: "¿Qué estructura de la flor produce y alberga los granos de polen?",
    options: ["Antera del estambre", "Estigma del pistilo", "Cáliz de sépalos", "Receptáculo floral"],
    correctIndex: 0,
    explanation: "La antera, situada en el extremo del filamento estaminal, contiene los sacos polínicos donde se forma el polen haploide."
  },
  {
    levelId: 1,
    question: "¿Cómo se llama la parte femenina reproductora completa de una flor?",
    options: ["Gineceo o pistilo", "Androceo", "Corola", "Periantio"],
    correctIndex: 0,
    explanation: "El gineceo está compuesto por uno o más carpelos, formando el estigma, el estilo y el ovario que encierra los óvulos."
  },
  {
    levelId: 1,
    question: "¿Cuál es la función principal de los sépalos en el capullo floral?",
    options: ["Proteger la yema floral antes de abrirse", "Atraer polinizadores nocturnos", "Almacenar azúcares elaborados", "Captar fotones solares"],
    correctIndex: 0,
    explanation: "Los sépalos componen el cáliz verde y protegen las delicadas piezas florales internas durante el desarrollo del botón."
  },

  // Nivel 2: Fotosíntesis Básica
  {
    levelId: 2,
    question: "¿Cuáles son los reactivos fundamentales que consumen las plantas para fotosintetizar?",
    options: ["Dióxido de carbono, agua y luz", "Oxígeno, glucosa y calor", "Nitrógeno, agua y sacarosa", "Metano, dióxido de carbono y sales"],
    correctIndex: 0,
    explanation: "La ecuación general fotosintética combina 6 CO2 + 6 H2O junto con energía lumínica para sintetizar glucosa C6H12O6 y liberar O2."
  },
  {
    levelId: 2,
    question: "¿Qué gas vital para los animales liberan las plantas como subproducto de la fotosíntesis?",
    options: ["Oxígeno molecular (O2)", "Dióxido de carbono (CO2)", "Monóxido de carbono (CO)", "Ozono gaseoso (O3)"],
    correctIndex: 0,
    explanation: "El oxígeno liberado procede directamente de la fotólisis (ruptura) de las moléculas de agua en el fotosistema II."
  },

  // Nivel 3: El Secreto de la Clorofila
  {
    levelId: 3,
    question: "¿Por qué la mayoría de las hojas se perciben de color verde a la vista humana?",
    options: ["Reflejan la longitud de onda verde y absorben azul y rojo", "Absorben exclusivamente luz verde", "Producen radiación fluorescente verde", "Contienen átomos de cobre oxidado"],
    correctIndex: 0,
    explanation: "La clorofila absorbe activamente la luz roja y azul del espectro visible, pero transmite y refleja la luz verde."
  },
  {
    levelId: 3,
    question: "¿Qué átomo metálico central se ubica en el anillo de porfirina de la clorofila?",
    options: ["Magnesio (Mg)", "Hierro (Fe)", "Calcio (Ca)", "Zinc (Zn)"],
    correctIndex: 0,
    explanation: "Un ión de magnesio se sitúa en el centro del anillo tetrapirrólico, esencial para la deslocalización de electrones excitados."
  },

  // Nivel 21: Reino Animal e Invertebrados
  {
    levelId: 21,
    question: "¿Aproximadamente qué porcentaje de todas las especies animales conocidas son invertebrados?",
    options: ["Más del 95%", "Alrededor del 50%", "Menos del 25%", "Exactamente el 70%"],
    correctIndex: 0,
    explanation: "Se estima que cerca del 97% de todas las especies de fauna descritas carecen de columna vertebral."
  },
  {
    levelId: 21,
    question: "¿Cuál de estos animales es un invertebrado?",
    options: ["Pulpo común", "Tiburón blanco", "Rana dardo", "Gorrión común"],
    correctIndex: 0,
    explanation: "Los pulpos son moluscos cefalópodos marinos y carecen por completo de huesos o columna vertebral articulada."
  },

  // Nivel 22: Artrópodos Dominantes
  {
    levelId: 22,
    question: "¿De qué polisacárido estructural resistente está compuesto el exoesqueleto de los artrópodos?",
    options: ["Quitina", "Celulosa", "Glucógeno", "Pectina"],
    correctIndex: 0,
    explanation: "La quitina es un polímero nitrogenado de N-acetilglucosamina que brinda ligereza, impermeabilidad y protección rígida."
  },

  // Nivel 41: El Filo Cordados
  {
    levelId: 41,
    question: "¿Cuál es una característica embrionaria compartida por todos los animales cordados?",
    options: ["Presencia de notocorda en alguna etapa", "Exoesqueleto de carbonato", "Corazón de dos cavidades", "Respiración exclusivamente traqueal"],
    correctIndex: 0,
    explanation: "Los cordados presentan en su desarrollo notocorda de sostén, cordón nervioso dorsal tubular, hendiduras branquiales y cola postanal."
  },

  // Nivel 48: Aves y la Mecánica del Vuelo
  {
    levelId: 48,
    question: "¿Qué adaptación ósea especial aligera el peso de las aves para facilitar el vuelo?",
    options: ["Huesos neumáticos con cavidades aéreas", "Huesos de cartílago puro", "Ausencia de caja torácica", "Huesos macizos de plomo biológico"],
    correctIndex: 0,
    explanation: "Los huesos de las aves son huecos y están interconectados con el sistema de sacos aéreos respiratorios."
  },

  // Nivel 69: La Doble Hélice del ADN
  {
    levelId: 69,
    question: "¿Cuáles son las bases nitrogenadas que forman pares complementarios mediante puentes de hidrógeno en el ADN?",
    options: ["Adenina con Timina, Citosina con Guanina", "Adenina con Guanina, Citosina con Timina", "Adenina con Uracilo, Guanina con Citosina", "Citosina con Uracilo, Timina con Guanina"],
    correctIndex: 0,
    explanation: "En la doble hélice de ADN, la Adenina se aparea con Timina (2 puentes de H) y la Citosina con Guanina (3 puentes de H)."
  },

  // Nivel 81: Selección Natural Darwiniana
  {
    levelId: 81,
    question: "¿Cuál es la premisa fundamental de la selección natural propuesta por Charles Darwin y Alfred Wallace?",
    options: ["Los individuos con variaciones ventajosas tienen mayor éxito reproductivo", "Los organismos mutan intencionalmente según su voluntad", "El ambiente no influye en la supervivencia de los linajes", "Todos los descendientes heredan caracteres idénticos sin variación"],
    correctIndex: 0,
    explanation: "La selección natural favorece la propagación de variantes genéticas que incrementan la adecuación biológica (fitness) en un entorno dado."
  }
];

// Fact pool for dynamically generating infinite biologically rigorous questions per level
const BIO_FACT_TEMPLATES = [
  {
    category: "Botánica",
    facts: [
      { subject: "El xilema", verb: "transporta", object: "agua y sales minerales desde la raíz", wrong: ["azúcares desde las hojas", "polen a los estigmas", "oxígeno a las raíces"], fact: "El xilema conduce la savia bruta unidireccionalmente gracias a la tensión-cohesión de la transpiración." },
      { subject: "El floema", verb: "distribuye", object: "la savia elaborada con sacarosa por toda la planta", wrong: ["agua cruda subterránea", "dióxido de carbono ambiental", "iones de magnesio exclusivamente"], fact: "El floema está formado por tubos cribosos y células acompañantes activas." },
      { subject: "Los estomas foliares", verb: "se abren mediante", object: "células oclusivas o de guarda turgentes", wrong: ["esporangios secos", "cutícula lignificada", "pelos glandulares"], fact: "Cuando las células oclusivas acumulan potasio y agua, se hinchan arqueándose y abren el ostiolo." },
      { subject: "Las gimnospermas", verb: "se caracterizan por", object: "producir semillas desnudas sin encerrar en un fruto", wrong: ["flores con cáliz vistoso", "frutos carnosos jugosos", "reproducirse solo por esquejes"], fact: "Las coníferas como pinos y cedros desarrollan conos o piñas leñosas con semillas aladas." },
      { subject: "El cambium vascular", verb: "es el tejido vegetal responsable de", object: "el crecimiento secundario en grosor del tronco", wrong: ["el crecimiento apical de la raíz", "la germinación de semillas", "la maduración de pétalos"], fact: "El cambium produce xilema secundario hacia el interior y floema secundario hacia el exterior." },
      { subject: "Las micorrizas", verb: "constituyen", object: "una simbiosis mutualista entre hongos y raíces de plantas", wrong: ["una infección parasitaria letal", "un tipo de alga carnívora", "una hormona de crecimiento"], fact: "Más del 80% de las plantas terrestres dependen de micorrizas para absorber fósforo y agua eficientemente." },
      { subject: "La transpiración vegetal", verb: "ocurre principalmente a través de", object: "los estomas presentes en el envés de las hojas", wrong: ["la cofia radicular", "el duramen del leño", "los zarcillos trepadores"], fact: "La transpiración crea una presión negativa crucial que succiona agua desde el subsuelo hasta la copa." },
      { subject: "La cutícula cerosa", verb: "cumple la función de", object: "evitar la pérdida excesiva de agua por evaporación", wrong: ["atraer insectos devoradores", "bloquear toda la fotosíntesis", "absorber nitrógeno atmosférico"], fact: "La cutina hidrofóbica protege la epidermis vegetal en climas cálidos y áridos." },
      { subject: "Las briofitas como los musgos", verb: "carecen de", object: "tejidos vasculares verdaderos de xilema y floema", wrong: ["clorofila fotosintética", "núcleo celular eucariota", "membrana de celulosa"], fact: "Los musgos absorben agua por capilaridad superficial directa debido a su pequeño porte." },
      { subject: "El fototropismo positivo", verb: "está mediado por", object: "la redistribución de la hormona auxina en el lado sombreado", wrong: ["la evaporación de savia", "la contracción de cloroplastos", "la rotura de paredes celulares"], fact: "Las auxinas provocan la elongación celular en el lado oscuro del tallo, curvándolo hacia la luz." }
    ]
  },
  {
    category: "Zoología",
    facts: [
      { subject: "Los cefalópodos como el pulpo", verb: "poseen en su piel", object: "cromatóforos controlados por el sistema nervioso", wrong: ["placas óseas duras", "pelos sensoriales de queratina", "escamas dérmicas placoideas"], fact: "Los cromatóforos se expanden o contraen en milisegundos permitiendo un camuflaje prodigioso." },
      { subject: "Los insectos respiran mediante", verb: "una red de conductos denominada", object: "sistema traqueal que lleva aire directamente a las células", wrong: ["pulmones en libro pleurales", "branquias cutáneas branquiales", "hemoglobina en sangre venosa"], fact: "Los espiráculos laterales dan paso a tráqueas ramificadas que no requieren transporte sanguíneo de O2." },
      { subject: "Las estrellas de mar", verb: "se desplazan gracias a", object: "su sistema ambulacral y pies tubulares hidráulicos", wrong: ["aletas natatorias pares", "patas articuladas de quitina", "músculos ciliares dorsales"], fact: "El sistema vascular acuífero utiliza presión hidráulica para extender los podios y aferrarse al sustrato." },
      { subject: "Las aves", verb: "poseen una estructura digestiva trituradora llamada", object: "molleja muscular provista de piedrecillas", wrong: ["buche glandular", "ciego cólico", "estómago ruminal"], fact: "Al no tener dientes, la molleja de paredes gruesas tritura semillas duras con ayuda de gastrolitos." },
      { subject: "Los peces condrictios", verb: "se diferencian por tener", object: "un esqueleto enteramente constituido de cartílago", wrong: ["huesos neumáticos densos", "pulmones primitivos pares", "pico córneo afilado"], fact: "Tiburones, rayas y quimeras poseen piel cubierta de dentículos dérmicos y carecen de vejiga natatoria." },
      { subject: "Los anfibios adultos", verb: "pueden respirar a través de", object: "la piel húmeda mediante respiración cutánea", wrong: ["branquias plumosas internas", "tráqueas de quitina", "plumas hidrófobas"], fact: "Su piel fina y ricamente vascularizada exige humedad constante para difundir el oxígeno." },
      { subject: "Los mamíferos monotremas", verb: "son singulares porque", object: "ponen huevos con cáscara correosa pero amamantan crías", wrong: ["poseen sangre fría invariable", "tienen alas con plumas", "carecen de glándulas mamarias"], fact: "El ornitorrinco y el equidna son los únicos mamíferos ovíparos vivos de la Tierra." },
      { subject: "Las serpientes huelen mediante", verb: "su lengua bífida asociada al", object: "órgano vomeronasal o de Jacobson", wrong: ["conducto auditivo medio", "seno maxilar frontal", "órgano sensorial de la línea lateral"], fact: "La lengua retrae partículas olorosas del aire y las deposita en el paladar para su análisis químico." },
      { subject: "Los cnidarios como las anémonas", verb: "capturan presas usando", object: "células urticantes especializadas llamadas cnidocitos", wrong: ["mandíbulas de queratina", "garras queladas retráctiles", "lenguas raspadoras rádulas"], fact: "Los nematocistos disparan un filamento con toxinas paralizantes al menor contacto físico." },
      { subject: "Los anélidos presentan un cuerpo", verb: "organizado en", object: "segmentos repetidos llamados metámeros", wrong: ["tres tagmas rígidos", "dos valvas calcáreas", "una concha en espiral"], fact: "Cada metámero contiene compartimentos celómicos y pares de órganos excretores metanefridiales." }
    ]
  },
  {
    category: "Fisiología y Genética",
    facts: [
      { subject: "La bomba Sodio-Potasio (Na+/K+)", verb: "expulsa y bombea respectivamente", object: "3 iones Na+ hacia afuera y 2 iones K+ hacia adentro por cada ATP", wrong: ["2 Na+ afuera y 3 K+ adentro", "1 Na+ adentro y 1 K+ afuera", "4 iones calcio hacia afuera"], fact: "Este transporte activo primario es vital para mantener el potencial de reposo en neuronas." },
      { subject: "Durante la fotosíntesis, la enzima Rubisco", verb: "cataliza", object: "la fijación de CO2 a la ribulosa-1,5-bisfosfato", wrong: ["la hidrólisis directa de glucosa", "la síntesis de ADN polimerasa", "la degradación de almidón"], fact: "La Rubisco es probablemente la proteína más abundante en la biosfera terrestre." },
      { subject: "En la traducción genética, el codón de inicio AUG", verb: "especifica el aminoácido", object: "Metionina en eucariotas", wrong: ["Alanina universal", "Valina estructural", "Triptófano catalítico"], fact: "Casi todas las cadenas polipeptídicas comienzan con metionina durante su síntesis ribosómica." },
      { subject: "El entrecruzamiento o crossing-over", verb: "ocurre durante", object: "la profase I de la meiosis", wrong: ["la metafase de la mitosis", "la anafase II meiótica", "la interfase fase G1"], fact: "El intercambio de segmentos entre cromátidas homólogas genera una diversidad genética infinita." },
      { subject: "Los telómeros son secuencias", verb: "situadas en", object: "los extremos de los cromosomas que protegen el ADN de degradación", wrong: ["el centro del nucléolo", "la membrana plasmática externa", "los ribosomas citoplasmáticos"], fact: "Con cada división celular los telómeros se acortan, vinculándose al envejecimiento celular." }
    ]
  },
  {
    category: "Ecología y Evolución",
    facts: [
      { subject: "La selección estabilizadora", verb: "actúa favoreciendo", object: "los fenotipos intermedios frente a los valores extremos", wrong: ["solo los fenotipos mutantes gigantes", "la extinción total de la población", "la separación en dos nuevas especies"], fact: "Por ejemplo, el peso al nacer en bebés humanos se mantiene en un rango óptimo por selección estabilizadora." },
      { subject: "El efecto fundador es un caso de", verb: "deriva génica donde", object: "un grupo pequeño coloniza un nuevo hábitat con baja variación genética", wrong: ["selección sexual competitiva", "fusión de dos continentes", "duplicación de todo el genoma"], fact: "Frecuencias alélicas raras pueden volverse muy comunes simplemente por azar en la nueva colonia." },
      { subject: "Una especie clave o keystone", verb: "se define como aquella que", object: "ejerce un impacto desproporcionadamente grande en su ecosistema", wrong: ["es la más abundante en número de individuos", "se alimenta únicamente de plantas invasoras", "carece de depredadores naturales"], fact: "Eliminar una especie clave (como la nutria marina) suele colapsar toda la red trófica circundante." },
      { subject: "La coevolución antagónica", verb: "se evidencia en", object: "carreras armamentistas entre depredadores veloces y presas ágiles", wrong: ["la fotosíntesis de líquenes", "la hibernación invernal de osos", "el polimorfismo de grupos sanguíneos"], fact: "Guepardos y gacelas han coevolucionado incrementando mutuamente sus velocidades punta." },
      { subject: "La sucesión ecológica primaria", verb: "se inicia en", object: "un terreno virgen desprovisto de suelo orgánico previo como roca volcánica", wrong: ["un bosque tras un incendio leve", "un campo de cultivo abandonado", "un lago con sedimentos ricos"], fact: "Líquenes y musgos pioneros descomponen la roca viva para formar la primera capa de tierra fértil." }
    ]
  }
];

// Generate 50 questions for a given level
function generateQuestionsForLevel(levelId) {
  const level = LEVELS_METADATA.find(l => l.id === levelId) || LEVELS_METADATA[0];
  const tier = getTierForLevel(levelId);
  const questions = [];

  // 1. Add specific curated questions for this level if present
  const curated = CURATED_QUESTIONS.filter(q => q.levelId === levelId);
  curated.forEach(q => questions.push({ ...q }));

  // 2. Select matching fact templates according to level's topic & category
  let categoryPool = BIO_FACT_TEMPLATES.find(p => p.category.toLowerCase().includes(level.category.toLowerCase()));
  if (!categoryPool) categoryPool = BIO_FACT_TEMPLATES[0];

  const allFacts = [];
  BIO_FACT_TEMPLATES.forEach(p => allFacts.push(...p.facts));

  // Seeded random helper so questions are consistent per level
  let seed = levelId * 7919;
  function pseudoRandom() {
    seed = (seed * 9301 + 49297) % 233280;
    return seed / 233280;
  }

  // Generate varied questions combining facts with level theme
  let factIdx = 0;
  while (questions.length < 50) {
    const currentFact = allFacts[(factIdx + levelId * 3) % allFacts.length];
    factIdx++;

    const questionTypes = [
      {
        q: `¿Cuál de las siguientes afirmaciones respecto a ${currentFact.subject.toLowerCase()} es biológicamente correcta?`,
        correct: `${currentFact.subject} ${currentFact.verb} ${currentFact.object}.`,
        distractors: currentFact.wrong.map(w => `${currentFact.subject} ${currentFact.verb} ${w}.`),
        explanation: currentFact.fact
      },
      {
        q: `En el estudio de ${level.title} (${tier.title}), ¿qué función o rasgo distingue a ${currentFact.subject.toLowerCase()}?`,
        correct: `${currentFact.verb.charAt(0).toUpperCase() + currentFact.verb.slice(1)} ${currentFact.object}.`,
        distractors: currentFact.wrong.map(w => `${currentFact.verb.charAt(0).toUpperCase() + currentFact.verb.slice(1)} ${w}.`),
        explanation: `Concepto clave de ${tier.name}: ${currentFact.fact}`
      },
      {
        q: `Durante el nivel ${levelId} (${level.title}), se analiza: ¿Qué relación tiene ${currentFact.subject.toLowerCase()} con su entorno biológico?`,
        correct: `Se relaciona porque ${currentFact.verb} ${currentFact.object}.`,
        distractors: currentFact.wrong.map(w => `Se relaciona porque ${currentFact.verb} ${w}.`),
        explanation: currentFact.fact
      }
    ];

    const chosenType = questionTypes[(questions.length + levelId) % questionTypes.length];
    
    // Shuffle options reproducibly
    const rawOptions = [chosenType.correct, ...chosenType.distractors];
    // Fisher-Yates with pseudoRandom
    for (let i = rawOptions.length - 1; i > 0; i--) {
      const j = Math.floor(pseudoRandom() * (i + 1));
      const temp = rawOptions[i];
      rawOptions[i] = rawOptions[j];
      rawOptions[j] = temp;
    }

    const correctIndex = rawOptions.indexOf(chosenType.correct);

    questions.push({
      id: `${levelId}_${questions.length + 1}`,
      levelId: levelId,
      question: chosenType.q,
      options: rawOptions,
      correctIndex: correctIndex,
      explanation: chosenType.explanation
    });
  }

  // Also integrate any custom questions created by the user for this level or category
  if (window.storage && window.storage.customQuestions) {
    const custom = window.storage.customQuestions.filter(q => q.levelId === levelId || (!q.levelId && q.category === level.category));
    custom.forEach(cq => questions.unshift(cq));
  }

  return questions;
}

// Sample 10 questions for a game session
function getQuizSessionQuestions(levelId) {
  const all = generateQuestionsForLevel(levelId);
  // Shuffle array
  const shuffled = [...all];
  for (let i = shuffled.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
  }
  return shuffled.slice(0, 10);
}

window.BioData = {
  DIFFICULTY_TIERS,
  LEVELS_METADATA,
  getTierForLevel,
  generateQuestionsForLevel,
  getQuizSessionQuestions
};
