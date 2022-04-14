package stalkervr.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import stalkervr.library.entity.Publication;
import stalkervr.library.entity.UserLibrary;
import stalkervr.library.service.PublicationService;
import stalkervr.library.service.UserLibraryService;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LibraryApplication {

	private final PublicationService publicationService;
	private final UserLibraryService userLibraryService;

	@Autowired
	public LibraryApplication(PublicationService publicationService, UserLibraryService userLibraryService) {
		this.publicationService = publicationService;
		this.userLibraryService = userLibraryService;
	}

	@PostConstruct
	public void fillTestDataBase(){

		UserLibrary userLibrary = new UserLibrary();
		UserLibrary userLibrary1 = new UserLibrary();


		Publication publication1 = new Publication("Портрет Дориана Грея", "\"Портрет Дориана Грея\" - самое знаменитое произведение Оскара Уайльда, единственный его роман, вызвавший в свое время шквал негативных оценок и тем не менее имевший невероятный успех.\n" +
				"Главный герой романа, красавец Дориан, - фигура двойственная, неоднозначная. Тонкий эстет и романтик становится безжалостным преступником. Попытка сохранить свою необычайную красоту и молодость оборачивается провалом. Вместо героя стареет его портрет - но это не может продолжаться вечно, и смерть Дориана расставляет все по своим местам.\n" +
				"Подробнее: https://www.labirint.ru/books/450144/", 5);
		Publication publication2 = new Publication("Повелитель мух", "\"Повелитель мух\". Странная, страшная и бесконечно притягательная книга. История благовоспитанных мальчиков, внезапно оказавшихся на необитаемом острове. Философская притча о том, что может произойти с людьми, забывшими о любви и милосердии. Гротескная антиутопия, роман-предупреждение и, конечно, напоминание о хрупкости мира, в котором живем мы все.Роман Уильяма Голдинга \"Повелитель мух\" (1954) – антиутопия с символическим подтекстом, исследующая особенности взаимоотношений детей на необитаемом острове, куда они попали в военное время.\n" +
				"Подробнее: https://www.labirint.ru/books/420218/", 3);
		Publication publication3 = new Publication("Загадка Ситтафорда", "Кому понадобилась смерть не имеющего врагов отставного военного, безобидного любителя газетных конкурсов и головоломок? Как получилось, что это убийство было предсказано во время спиритического сеанса в деревушке, отрезанной от мира небывалым снегопадом? Кто все-таки сможет разгадать сложнейшую «Загадку Ситтафорда»?\n" +
				"Подробнее: https://www.labirint.ru/books/853933/", 7);
		Publication publication4 = new Publication("Хоббит", "\"В земле была нора, а в норе жил Хоббит\". Эти слова написал Джон Рональд Руэл Толкин на обороте школьной экзаменационной работы, которую проверял одним жарким летним днем. И кто бы мог подумать, что именно из них, как из волшебного зернышка, произрастет одно из самых известных произведений мировой литературы...\n" +
				"Подробнее: https://www.labirint.ru/books/853523/", 6);
		Publication publication5 = new Publication("Скотный двор", "Повесть-притча Джорджа Оруэлла об эволюции общества животных, изгнавших со скотного двора его предыдущего владельца, жестокого мистера Джонса, от безграничной свободы к диктатуре.\n" +
				"Иллюстрации Криса Моулда, британского иллюстратора и автора художественных книг, удостоенного многочисленных наград\n" +
				"Подробнее: https://www.labirint.ru/books/853016/", 10);
		Publication publication6 = new Publication("Мещанин во дворянстве и другие пьесы", "Господин Журден страстно мечтает попасть в высшее общество. Для этого он изо всех сил пытается подражать дворянам: заказывает портному костюмы, которые на самом деле выглядят нелепо; берет уроки музыки, танцев и фехтования, ничего в этом не понимая, а лишь растрачивая деньги на бесконечных учителей. \"Оказывается, мы разговариваем прозой!\" - с восторгом говорит он жене, демонстрируя свежеиспеченную образованность. И не замечает, что все потешаются над ним…\n" +
				"Хитрец по имени Тартюф, прикинувшись святошей, пользуется доверием хозяина и прибирает к рукам весь дом. Казалось бы, все это видят - но только не господин Оргон и его матушка, которым \"благочестивый ученый\" стал ближе всех родственников. Благодаря бессмертной комедии имя Тартюф стало нарицательным.\n" +
				"Подробнее: https://www.labirint.ru/books/850985/", 2);
		Publication publication7 = new Publication("Белая роза","Англия, конец XV века. Война двух роз, Алой - Ланкастеров и Белой - Йорков, закончилась вничью. Погибли все. На пепелище прибыл новый игрок - Тюдор, занявший пустой трон под именем Генриха VII. Чтобы навести порядок после разрухи в стране и головах англичан, Генрих объединил в своем гербе оба цветка и прибрал к рукам буквально всё, что принадлежало проигравшим. Но месть - это блюдо, которое надо подавать холодным. Белой розой растет в сердце Маргариты Йоркской мечта о реванше. Она не сидит на месте, готовит интригу за интригой, ведет поиски нужного человека. И вот, наконец! Крик в ночи. Неожиданная встреча среди снегов и горных вершин. Обернется ли она той самой долгожданной удачей или станет роковой?..\n" +
				"Подробнее: https://www.labirint.ru/books/850646/",5);
		Publication publication8 = new Publication("Без судьбы","\"Без судьбы\" - исповедь венгерского писателя, лауреата Нобелевской премии по литературе Имре Кертеса, который попал в концлагерь в возрасте 15 лет, смог пройти все ужасы заключения и остаться в живых.\n" +
				"Невзирая на трагедию, муки голода, потерю родных и близких, герой учится принимать свою судьбу и даже в жестоких и нечеловеческих условиях он пытается обрести надежду: \"…я ощутил, как растет, как копится во мне готовность: я буду продолжать свою, не подлежащую продолжению, жизнь. …Нет в мире такого, чего бы мы не пережили как нечто совершенно естественное; и на пути моем, я знаю, меня подстерегает, словно какая-то неизбежная западня - счастье\".\n" +
				"Подробнее: https://www.labirint.ru/books/848072/",4);
		Publication publication9 = new Publication("Превращение","Франц Кафка - самая странная фигура европейской литературы ХХ столетия. Критики причисляют его ко всем литературным направлениям по очереди, называя и основоположником абсурдизма, и классиком магического реализма, и мастером модернизма, и предшественником экзистенциалистов. Его герои ищут ответы на незаданные вопросы, блуждая в тумане, на грани реальности, пустоты и ужаса, приковывая к себе взгляды все новых поколений читателей. Трагическая обреченность столкновения \"маленького\" человека с парадоксальностью жизни, человека и общества, человека и Бога, кошмарные, фантастические, гротескные ситуации, - в сборнике представлены самые известные произведения великого австрийца, рассказы и притчи.\n" +
				"Подробнее: https://www.labirint.ru/books/847698/",8);
		Publication publication10 = new Publication("Любовь к жизни","Двое золотоискателей переходили реку вброд. Один уже перешел, а второй подвернул ногу на скользком камне. \"Билл!\" - окликнул пострадавший, но Билл не оглянулся и вскоре скрылся из виду.\n" +
				"Путь лежал через равнину. У золотоискателя распухла нога, развалилась обувь, заканчивались припасы. Впереди - дорога длиной в несколько дней. Через голод, недомогание, слабость, встречи с волками и медведем, золотоискатель потеряет все добытое золото, которое добыл, но не утратит главного - любви к жизни.\n" +
				"Подробнее: https://www.labirint.ru/books/845110/",10);


		publicationService.addPublication(publication1);
		publicationService.addPublication(publication2);
		publicationService.addPublication(publication3);
		publicationService.addPublication(publication4);
		publicationService.addPublication(publication5);
		publicationService.addPublication(publication6);
		publicationService.addPublication(publication7);
		publicationService.addPublication(publication8);
		publicationService.addPublication(publication9);
		publicationService.addPublication(publication10);

		userLibraryService.addUser(userLibrary);
		userLibraryService.addUser(userLibrary1);

		userLibraryService.takeBook(userLibrary, publication1);
		userLibraryService.takeBook(userLibrary1, publication5);
		userLibraryService.takeBook(userLibrary, publication5);

		userLibraryService.returnBook(userLibrary, publication5);

	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
